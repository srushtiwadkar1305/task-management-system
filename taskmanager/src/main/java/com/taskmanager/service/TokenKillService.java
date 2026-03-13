package com.taskmanager.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenKillService {

    private Set<String> tokenBlockList = ConcurrentHashMap.newKeySet(); 

    public void addTokenToBlockList(String token){
        tokenBlockList.add(token);
    }

    public boolean isTokenInBlockList(String token){
        return tokenBlockList.contains(token);
    }

}
