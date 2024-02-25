package com.example.wallet_service.Model;

import java.util.List;

public class UserResponse {
     private List<User> usersList;

     public List<User> getUsers() {
         return usersList;
     }

     public void setUsers(List<User> usersList) {
         this.usersList = usersList;
     }

}
