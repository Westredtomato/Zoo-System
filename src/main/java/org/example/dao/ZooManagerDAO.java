package org.example.dao;

/**
 * 已弃用：原来的 ZooManagerDAO 依赖旧版实体和 DAO 接口（如 Species/Visitor 等），
 * 与当前 schema.sql 中的表结构不匹配。为避免编译错误，这里暂时保留空壳类，
 * 后续如果需要综合统计功能，可以基于新的实体/DAO 重新设计实现。
 */
public class ZooManagerDAO {
    // TODO: 根据新的 animal / enclosure / employee / feed / feeding_record 等表，
    // 重新设计动物园统计与业务聚合方法。
}