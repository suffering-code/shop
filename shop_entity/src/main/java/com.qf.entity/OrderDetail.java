package com.qf.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "t_order_detail")
public class OrderDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.id
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.oid
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private String oid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.gid
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private Integer gid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.gname
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private String gname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.gpic
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private String gpic;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.gprice
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private Double gprice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.num
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private Integer num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.sub_total
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private Double subTotal;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_order_detail.gdesc
     *
     * @mbggenerated Mon Sep 16 15:43:27 CST 2019
     */
    private String gdesc;

}