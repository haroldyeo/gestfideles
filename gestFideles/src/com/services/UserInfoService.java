/* 
	Description:
		ZK Essentials
	History:
		Created by dennis

Copyright (C) 2012 Potix Corporation. All Rights Reserved.
*/
package com.services;

import com.entities.User;

public interface UserInfoService {

	/** find user by account **/
	public User findUser(String account);
	
	/** update user **/
	public User updateUser(User user);
}
