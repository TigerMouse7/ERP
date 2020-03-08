package com.yuhannci.erp.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class GeneralVacationSearch extends DataTableRequestBase {
	
	String usrName;
	String deptCode;
	
	Integer draw;
}
