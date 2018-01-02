package com.hisun.lemon.cpi.alipay.ebankpay;

import java.util.List;

/**
 * 商品信息
 * @author zhangzl
 *
 */
public class GoodsInfo {

	private List<Goods_Detail> goods_detail;
	
	public List<Goods_Detail> getGoods_detail() {
		return goods_detail;
	}

	public void setGoods_detail(List<Goods_Detail> goods_detail) {
		this.goods_detail = goods_detail;
	}

	public static class Goods_Detail {

		public Goods_Detail(String goods_id, String goods_name, int quantity, int price) {
			this.goods_id = goods_id;
			this.goods_name = goods_name;
			this.quantity = quantity;
			this.price = price;
		}

		public Goods_Detail(String goods_id, String goods_name, int quantity, int price, String goods_category, String body, String show_url) {
			this.goods_id = goods_id;
			this.goods_name = goods_name;
			this.quantity = quantity;
			this.price = price;
			this.goods_category = goods_category;
			this.body = body;
			this.show_url = show_url;
		}

		/**
		 * 商品编号
		 */
		private String goods_id;

		/**
		 * 商品名称
		 */
		private String goods_name;

		/**
		 * 商品数量
		 */
		private int quantity;

		/**
		 * 商品单价，单位为元
		 */
		private int price;

		/**
		 * 商品类目
		 */
		private String goods_category;

		/**
		 * 商品描述信息
		 */
		private String body;

		/**
		 * 商品的展示地址
		 */
		private String show_url;


		/**
		 * 商品编号
		 */
		public String getGoods_id() {
			return goods_id;
		}

		/**
		 * 商品编号
		 */
		public void setGoods_id(String goods_id) {
			this.goods_id = goods_id;
		}


		/**
		 * 商品名称
		 */
		public String getGoods_name() {
			return goods_name;
		}

		/**
		 * 商品名称
		 */
		public void setGoods_name(String goods_name) {
			this.goods_name = goods_name;
		}

		/**
		 * 商品数量
		 */
		public int getQuantity() {
			return quantity;
		}

		/**
		 * 商品数量
		 */
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		/**
		 * 商品单价，单位为分
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * 商品单价，单位为分
		 */
		public void setPrice(int price) {
			this.price = price;
		}

		public String getGoods_category() {
			return goods_category;
		}

		public void setGoods_category(String goods_category) {
			this.goods_category = goods_category;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getShow_url() {
			return show_url;
		}

		public void setShow_url(String show_url) {
			this.show_url = show_url;
		}
	}

}
