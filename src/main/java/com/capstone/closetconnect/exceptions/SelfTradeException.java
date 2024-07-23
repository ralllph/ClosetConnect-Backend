package com.capstone.closetconnect.exceptions;

public class SelfTradeException extends  RuntimeException{

    public SelfTradeException(){ super("Trade Invalid. Trade can only occur between separate users"); }

}
