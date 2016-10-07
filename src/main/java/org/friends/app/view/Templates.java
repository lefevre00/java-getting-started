/**
 * EcoParking v1.2
 * 
 * Application that allows management of shared parking 
 * among multiple users.
 * 
 * This file is copyrighted in LGPL License (LGPL)
 * 
 * Copyright (C) 2016 M. Lefevre, A. Tamditi, W. Verdeil
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.friends.app.view;

public interface Templates {
	static final String INDEX = "index.ftl";
	static final String ERROR = "error.ftl";

	static final String MESSAGE_OK_KO = "token.ftl";
	static final String SETTING = "setting.ftl";
	static final String PASSWORD_NEW = "pwd_new.ftl";
	static final String PASSWORD_LOST = "pwd_lost.ftl";
	static final String PASSWORD_CHANGE = "change_pwd.ftl";
	static final String LOGIN = "login.ftl";
	static final String LOGOUT = "logout.ftl";
	static final String REGISTER = "createUser.ftl";

	static final String SEND_MAIL = "mail_send.ftl";
	static final String SEND_CONTACT = "contactSend.ftl";

	static final String RESERVATIONS = "reservations.ftl";
	static final String BOOK = "book.ftl";
	static final String SEARCH = "search.ftl";
	static final String SHARE = "sharePlace.ftl";
	static final String STATISTICS = "statistics.ftl";
	
	static final String ADMIN_PAGE = "adminPage.ftl";
	static final String ACCESS_DENIED = "accessDenied.ftl";
	static final String USERS_LIST = "usersList.ftl";
	static final String USER_EDIT = "userEdit.ftl";
	
	static final String ADMIN_SHARE = "adminShare.ftl";
	static final String ADMIN_CREATE = "adminCreate.ftl";

	static final String STATS_DETAIL = "statsDetail.ftl";
}
