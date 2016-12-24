package com.example.myprojects.model;

import java.io.Serializable;

/**
 * CREATE TABLE [dbo].[executeCount](
 * [id] [bigint] IDENTITY(1,1) NOT NULL,
 * [recordAt] [datetime] NOT NULL,
 * [appCode] [varchar](20) NOT NULL,
 * [ip] [varchar](20) NOT NULL,
 * [type] [varchar](20) NOT NULL,
 * [item] [varchar](100) NOT NULL,
 * [num] [bigint] NOT NULL,
 * [sumTime] [bigint] NOT NULL,
 * PRIMARY KEY CLUSTERED
 * (
 * [id] ASC
 * )
 *
 * @author wangyao
 * @since 2016-2-29 16:32:08
 */
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1753504898074137759L;

	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return this.username + "\t" + this.password + "<br>";
	}
	
}