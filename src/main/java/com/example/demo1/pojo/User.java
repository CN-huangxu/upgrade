package com.example.demo1.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Administrator
 */

@Getter
@Setter
public	class	User implements Serializable {
   private	Integer	id;
    private	String	username;
    private	Integer	age;


}