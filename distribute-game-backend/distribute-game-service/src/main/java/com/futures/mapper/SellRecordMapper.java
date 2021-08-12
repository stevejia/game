package com.futures.mapper;

import com.futures.model.entity.SellRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 
 * @author code-generator
 * @date 2020-06-22 10:32:30
 */
@Mapper
public interface SellRecordMapper {

    @Select("select * from zp_sell_record where status = #{status}")
    List<SellRecord> findList(@Param("status") Integer status);

    /**
     * 根据主键获取实体
     */
    @Select("SELECT `id`, `user_id`, `sell_incom`, `pig_level`, `sell_date`, `status` FROM zp_sell_record WHERE `id` = #{id}")
    SellRecord get(@Param("id") Long id);

    @Select("select * from zp_sell_record where user_id = #{userId} and TO_DAYS(sell_date) = TO_DAYS(NOW())")
    List<SellRecord> getByUserAndNow(@Param("userId") Long userId);

    /**
     * 新增记录
     * @param zpSellRecord
     * @return
     */
    @InsertProvider(type = ZpSellRecordMapperProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long insert(SellRecord zpSellRecord);

    /**
     * 根据主键删除记录
     * @return
     */
    @Delete("DELETE FROM zp_sell_record WHERE `id` = #{id}")
    long delete(@Param("id") Long id);

    /**
     * 更新实体信息
     * @param zpSellRecord
     */
    @UpdateProvider(type = ZpSellRecordMapperProvider.class, method = "update")
    long update(SellRecord zpSellRecord);

    /**
     * 更新实体信息，null值字段不更新
     * @param zpSellRecord
     * @return
     */
    @UpdateProvider(type = ZpSellRecordMapperProvider.class, method = "updateIgnoreNull")
    long updateIgnoreNull(SellRecord zpSellRecord);

    class ZpSellRecordMapperProvider {
        public String insert() {
            return new SQL(){{
                INSERT_INTO("zp_sell_record");
                VALUES("user_id", "#{userId}");
                VALUES("pig_level", "#{pigLevel}");
                VALUES("sell_incom", "#{sellIncom}");
                VALUES("sell_date", "#{sellDate}");
                VALUES("status", "#{status}");
            }}.toString();
        }

        public String update(SellRecord zpSellRecord) {
            return new SQL(){{
                UPDATE("zp_sell_record");
                SET("`user_id` = #{userId}");
                SET("`pig_level` = #{pigLevel}");
                SET("`sell_incom` = #{sellIncom}");
                SET("`sell_date` = #{sellDate}");
                SET("`status` = #{status}");
                WHERE("`id` = #{id}");
            }}.toString();
        }

        public String updateIgnoreNull(SellRecord zpSellRecord) {
            return new SQL(){{
                UPDATE("zp_sell_record");
                if (zpSellRecord.getUserId() != null) {
                    SET("`user_id` = #{userId}");
                }
                if (zpSellRecord.getSellIncom() != null) {
                    SET("`sell_incom` = #{sellIncom}");
                }
                if (zpSellRecord.getPigLevel() != null) {
                    SET("`pig_level` = #{pigLevel}");
                }
                if (zpSellRecord.getSellDate() != null) {
                    SET("`sell_date` = #{sellDate}");
                }
                if (zpSellRecord.getStatus() != null) {
                    SET("`status` = #{status}");
                }
                WHERE("`id` = #{id}");
            }}.toString();
        }
    }

}