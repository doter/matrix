package com.matrix.core.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrix.core.exception.BizException;

public class FilterParser implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(FilterParser.class);
	
	public static final String OP_EQUAL = "eq";//等于
	public static final String OP_NOT_EQUAL = "ne";//不等于
	public static final String OP_LESS_THAN = "lt";//小于
	public static final String OP_GREATER_THAN = "gt";//大于
	public static final String OP_LESS_OR_EQUAL = "le";//小于等于
	public static final String OP_GREATER_OR_EQUAL = "ge";//大于等于
	public static final String OP_LIKE = "like";////模糊匹配
	public static final String OP_ILIKE = "ilike";//模糊匹配，不区分大小写
	public static final String OP_BW = "bw";//开始于 like s%
	public static final String OP_BN = "bn";//不开始于 not like s%
	public static final String OP_EW = "ew";//结束于 like %s
	public static final String OP_EN = "en";//不结束于 not like %s
	
	public static final String OP_CN = "cn";//包含 in
	public static final String OP_NC = "nc";//不包含 not in
	public static final String OP_IN = "in"; // in 
	public static final String OP_NOT_IN = "nin"; //not in
	
	public static final String OP_EMPTY = "nu";//空值
	public static final String OP_NOT_EMPTY = "nn";//非空值
	
	public static final String OP_NULL = "null"; //is null
	public static final String OP_NOT_NULL = "nnull"; //is not null
	
	public static final String OP_PROPERTY = "field";
	public static final String OP_SYMBOL = "op";
	public static final String OP_VALUE = "data";
	
	

	/**
	 * 
	 * @param filters {"groupOp":"OR","rules":[{"field":"fullName","op":"nc","data":"sss"},{"field":"createTime","op":"eq","data":""},{"field":"id","op":"eq","data":""}]}
	 * @return[(String)expression,(List)values]
	 */
	public static Object[] parser(String filters){
		if(StringUtils.isEmpty(filters)){
			return null;
		}
		
		StringBuffer expression = new StringBuffer();
		List values = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map map = mapper.readValue(HtmlUtils.htmlUnescape(filters), Map.class);
			List<Map> filterItems = (List<Map>)map.get("rules");
			if(null != filterItems && filterItems.size() > 0){
				Map filterItem = null;
				int size = filterItems.size();
				values = new ArrayList(size);
				
				String[]  fs = new String[size];
				for(int i = 0; i < size; i++){
					filterItem = filterItems.get(i);
					if(OP_EQUAL.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} = ?"+(i+1);
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_NOT_EQUAL.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} != ?"+(i+1);
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_LESS_THAN.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} < ?"+(i+1);
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_GREATER_THAN.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} > ?"+(i+1);
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_LESS_OR_EQUAL.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} <= ?"+(i+1);
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_GREATER_OR_EQUAL.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} >= ?"+(i+1);
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_LIKE.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} LIKE ?"+(i+1);
						values.add("%" + (String)filterItem.get(OP_VALUE) + "%");
					}else if(OP_ILIKE.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} ILIKE ?"+(i+1);
						values.add("%" + (String)filterItem.get(OP_VALUE) + "%");
					}else if(OP_BW.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} LIKE ?"+(i+1);
						values.add((String)filterItem.get(OP_VALUE) + "%");
					}else if(OP_BN.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} NOT LIKE ?"+(i+1);
						values.add((String)filterItem.get(OP_VALUE) + "%");
					}else if(OP_EW.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} LIKE ?"+(i+1);
						values.add("%" + (String)filterItem.get(OP_VALUE));
					}else if(OP_EN.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} NOT LIKE ?"+(i+1);
						values.add("%" + (String)filterItem.get(OP_VALUE));
					}else if(OP_CN.equals(filterItem.get(OP_SYMBOL)) || OP_IN.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} IN (?"+(i+1) +")";
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_NC.equals(filterItem.get(OP_SYMBOL)) || OP_NOT_IN.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} NOT IN (?"+(i+1) +")";
						values.add(filterItem.get(OP_VALUE));
					}else if(OP_EMPTY.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "({"+filterItem.get(OP_PROPERTY) +"} IS NULL OR " + "{"+filterItem.get(OP_PROPERTY) +"} = '')";
					}else if(OP_NOT_EMPTY.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} != ''";
					}else if(OP_NULL.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} IS NULL";
					}else if(OP_NOT_NULL.equals(filterItem.get(OP_SYMBOL))){
						fs[i] = "{"+filterItem.get(OP_PROPERTY) +"} IS NOT NULL";
					}else{
						throw new BizException(filterItem.get(OP_SYMBOL)+"是非法操作符。");
					}
				}
				
				String mask = (String)map.get("groupOp");
				if("AND".equalsIgnoreCase(mask) || "OR".equalsIgnoreCase(mask)){
					for(int i = 0; i < fs.length; i++){
						if(i != 0){
							expression.append(map.get("groupOp"));
						}
						expression.append(" " + fs[i] +" ");
					}
					
					
				}else{
					String[] a = null;
					for(int i = 0; i < fs.length; i++){
						a = mask.split("#"+(i+1));
						expression.append(a[0]).append(fs[i]);
						mask = a[1];
					}
					expression.append(mask);
					logger.info("filters expression:\t"+expression.toString());
				}
			}
			
		} catch (Exception e) {
			logger.error("查询过滤解释出错：\n"+e.getMessage());
			throw new BizException("查询过滤解释出错：\n"+e.getMessage());
		}
		return new Object[]{expression.toString(),values};
	}
}