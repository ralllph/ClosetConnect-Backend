package com.capstone.closetconnect.services.clothing_items;

import com.capstone.closetconnect.dtos.request.ClothingItem;
import com.capstone.closetconnect.dtos.request.UpdateCloth;
import com.capstone.closetconnect.dtos.response.ClothDetailsWithUser;
import com.capstone.closetconnect.dtos.response.ClothingItemsDto;
import com.capstone.closetconnect.dtos.response.ActionSuccess;
import com.capstone.closetconnect.enums.ClothType;
import com.capstone.closetconnect.enums.Gender;
import com.capstone.closetconnect.exceptions.*;
import com.capstone.closetconnect.models.ClothingItems;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.ClothingItemsRepository;
import com.capstone.closetconnect.repositories.UserRepository;
import com.capstone.closetconnect.services.amazon_s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClothingItemsServiceImpl implements ClothingItemsService {

    private final UserRepository userRepository;
    private final ClothingItemsRepository clothingItemsRepository;
    private final S3Service s3Service;
    private final String S3_BUCKET = System.getenv("AWS_BUCKET_NAME");
    final int  MAX_IMAGE_SIZE = 5 * 1024 * 1024;;

    // Define allowed MIME types
    final Set<String> ALLOWED_MIME_TYPES = Set.of("image/jpeg", "image/png");

    @Override
    public Page<ClothingItemsDto> searchUserClothingItems
            (Long userId, String itemName, ClothType itemType, Gender gender, Pageable pageable) {
        if(itemName == null  && itemType ==null && gender==null)
            throw new MissingParameterException();
        userRepository.findById(userId).
                orElseThrow(() -> new NotFoundException(itemName, userId));
        Page<ClothingItems> clothingItemsFound = Page.empty(pageable);
        if(itemName!=null && !itemName.isEmpty()) {
           clothingItemsFound = clothingItemsRepository
                    .findByUserIdAndNameContaining(userId, itemName,pageable);
        }
        else if(itemType!=null ) {
            clothingItemsFound = clothingItemsRepository
                    .findByUserIdAndType(userId, itemType, pageable);
        }
        else if(gender!=null ) {
            clothingItemsFound = clothingItemsRepository
                    .findByUserIdAndGender(userId, gender, pageable);
        }
         return clothingItemsFound.map(this::clothingItemToDto);
    }

    @Override
    public Page<ClothDetailsWithUser> searchAllClothingItems
            (String itemName, ClothType itemType,
             Gender gender, Pageable pageable) {
        if(itemName == null  && itemType ==null && gender==null)
            throw new MissingParameterException();
        Page<ClothDetailsWithUser> clothingItemsFound = Page.empty(pageable);
        if(itemName!=null && !itemName.isEmpty()) {
            clothingItemsFound = clothingItemsRepository
                    .getAllClothingItemsByNameContaining(itemName,pageable);
        }
        else if(itemType!=null ) {
            clothingItemsFound = clothingItemsRepository
                    .getAllClothingItemsByType(itemType, pageable);
        }
        else if(gender!=null ) {
            clothingItemsFound = clothingItemsRepository
                    .getAllClothingItemsByGender(gender, pageable);
        }
        return clothingItemsFound;
    }

    @Override
    public void uploadClothingItemImage(Long clothId, MultipartFile file) {
        clothingItemsRepository.findById(clothId)
                .orElseThrow(()->new NotFoundException("cloth",clothId));
        String clothImageId = UUID.randomUUID().toString();

        try {
            //check image type
            String mimeType = file.getContentType();
            if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType)) {
                throw new InvalidImageException();
            }

            // Check the file size
            if (file.getSize() > MAX_IMAGE_SIZE) {
                throw new ImageTooLargeException(MAX_IMAGE_SIZE);
            }

            s3Service.putObject(
                    S3_BUCKET,
                    //key: store in bucket as  clothing-items/useriD/photo id(random digit
                    "clothing-items/%s/%s".formatted(clothId, clothImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            log.info("Error uploading image with error message {}", e.getMessage());
            // TODO:  generatecustom exception
            throw new RuntimeException(e);
        }
            updateClothingPhotoUrl(clothImageId,clothId);
    }

    @Override
    public byte[] getUserClothingImage(Long clothId) {
        ClothingItems clothFound = checkClothItemExists(clothId);
        ClothingItemsDto clothFoundDto = ClothingItems.toClothingItemDto(clothFound);
        if(clothFoundDto.getPhotoUrl().isBlank())
            throw new NotFoundException("clothing item photo url ", null);
        //check if profile image id is empty or not
        byte[] clothImage =  s3Service.getObject(
                S3_BUCKET,
                "clothing-items/%s/%s".formatted(clothId, clothFoundDto.getPhotoUrl())
        );
        return clothImage;
    }

    @Override
    public void updateClothingPhotoUrl(String clothPhotoUrl, Long clothId) {
       ClothingItems cloth =  checkClothItemExists(clothId);
        cloth.setPhotoUrl(clothPhotoUrl);
        clothingItemsRepository.save(cloth);
    }

    @Override
    public ClothingItemsDto createNewClothingItem(Long userId, ClothingItem newClothingItem) {
        User userUploading =  checkUserExist(userId);
        ClothingItems newCloth = ClothingItem.toClothingEntity(newClothingItem);
        newCloth.setUser(userUploading);
        clothingItemsRepository.save(newCloth);
        return ClothingItems.toClothingItemDto(newCloth);
    }

    @Override
    public ClothingItemsDto updateClothingItem
            (Long clothId, Long userId, UpdateCloth clothingItem) {
        User userUpdating = checkUserExist(userId);
        ClothingItems clothItemUpdating = checkClothItemExists(clothId);
        if(!clothItemUpdating.getUser().getId().equals(userUpdating.getId())){
            throw new NotAssociatedException("clothing item", "user");
        }
        ClothingItems updatedClothItem = updateClothingEntity(clothItemUpdating, clothingItem);
        return ClothingItems.toClothingItemDto(updatedClothItem);
    }

    @Override
    public ClothDetailsWithUser getClothingItem(Long clothId) {
        checkClothItemExists(clothId);
        return clothingItemsRepository.getClothingItemWithUserDetails(clothId)
                .orElseThrow(() -> new NotFoundException("cloth", clothId));
    }

    @Override
    public Page<ClothingItemsDto> getAllUserClothingItems(Long userId, Pageable pageable) {
        checkUserExist(userId);
        Page<User> userPage = userRepository.findUserWithClothingItems(userId,pageable);
        List<ClothingItems> userClothingItems = userPage.getContent().stream()
                .flatMap(user -> user.getClothingItems().stream())
                .collect(Collectors.toList());
        List<ClothingItemsDto> clothingItemsDtos = clothingItemsListToDto(userClothingItems);
        return new PageImpl<>(clothingItemsDtos,pageable,userPage.getTotalElements());
    }

    @Override
    public Page<ClothDetailsWithUser> getAllClothingItemsWithUserInfo(Boolean latest,
                                                                      Pageable pageable) {
        if(Boolean.TRUE.equals(latest)){
            LocalDate startDate= LocalDate.now().minusDays(7);
            Date startDateSql = Date.valueOf(startDate);
            return clothingItemsRepository
                    .getRecentClothingItemsWithUserInfo(startDateSql,pageable);
        }
        else {
            return clothingItemsRepository
                    .getAllClothingItemsWithUserInfo(pageable);
        }
    }

    @Override
    public ActionSuccess deleteClothingItem(Long clothId, Long userId) {
        User user= checkUserExist(userId);
        ClothingItems clothToBeDeleted = checkClothItemExists(clothId);
        //check if user owns cloth
        if(!clothToBeDeleted.getUser().getId().equals(user.getId())){
            throw new NotAssociatedException("cloth","user");
        }

        clothingItemsRepository.deleteClothingItem(clothId);

        return new ActionSuccess("Clothing item deleted successfuly");
    }


    private ClothingItemsDto  clothingItemToDto(ClothingItems clothingItems){
        return ClothingItems.toClothingItemDto(clothingItems);
    }

    private User checkUserExist(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("user", userId));
    }

    @Override
    public ClothingItems checkClothItemExists(Long clothId){
        return clothingItemsRepository.findById(clothId)
                .orElseThrow(()-> new NotFoundException("cloth", clothId));
    }

    private ClothingItems updateClothingEntity(ClothingItems clothToBeUpdated,
            UpdateCloth newClothDetails){
        if(newClothDetails.getName() != null){
            clothToBeUpdated.setName(newClothDetails.getName());
        }
        if(newClothDetails.getGender() !=null){
            clothToBeUpdated.setGender(newClothDetails.getGender());
        }
        if(newClothDetails.getDescription() != null){
            clothToBeUpdated.setDescription(newClothDetails.getDescription());
        }
        if(newClothDetails.getType() !=null){
            clothToBeUpdated.setType(newClothDetails.getType());
        }
        if(newClothDetails.getStatus()!=null){
            clothToBeUpdated.setStatus(newClothDetails.getStatus());
        }
        if(newClothDetails.getItemCondition()!=null){
            clothToBeUpdated.setItemCondition(newClothDetails.getItemCondition());
        }
        if(newClothDetails.getClothingItemSize()!=null){
            clothToBeUpdated.setClothingItemSize(newClothDetails.getClothingItemSize());
        }
        if(newClothDetails.getSource() != null){
            clothToBeUpdated.setSource(newClothDetails.getSource());
        }
        clothingItemsRepository.save(clothToBeUpdated);
        return clothToBeUpdated;
    }

    private List<ClothingItemsDto> clothingItemsListToDto(List<ClothingItems> clothingList){
        return clothingList.stream()
                .map(ClothingItems::toClothingItemDto)
                .collect(Collectors.toList());
    }


}