package com.capstone.closetconnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Objects;

@Table(name = "messages")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String content;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(sender, message.sender) && Objects.equals(receiver, message.receiver) && Objects.equals(content, message.content) && Objects.equals(createdAt, message.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, receiver, content, createdAt);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
