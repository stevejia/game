package com.gongyu.service.distribute.game.mapper;

import com.gongyu.service.distribute.game.model.entity.PersonAuthRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * 
 * @author code-generator
 * @date 2020-07-08 11:23:56
 */
@Mapper
public interface PersonAuthRecordMapper {
    /**
     * 根据主键获取实体
     */
    @Select("SELECT `id`, `name`, `id_card`, `certif_status`, `req_status`, `build_user_id`,`al_result_json`, `create_date`, `update_date` FROM person_auth_record WHERE `id` = #{id}")
    PersonAuthRecord get(@Param("id") Long id);

    @Select("select * from person_auth_record where name = #{name} and id_card = #{idCard} and req_status = 4 and certif_status =2")
    List<PersonAuthRecord> findByPerson(@Param("name") String name,@Param("idCard") String idCard);

    @Select("select * from person_auth_record where build_user_id = #{userId} and req_status = 4 and certif_status =2")
    PersonAuthRecord findByUserId(@Param("userId") Long userId);

    /**
     * 新增记录
     * @param personAuthRecord
     * @return
     */
    @InsertProvider(type = PersonAuthRecordMapperProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long insert(PersonAuthRecord personAuthRecord);

    /**
     * 根据主键删除记录
     * @return
     */
    @Delete("DELETE FROM person_auth_record WHERE `id` = #{id}")
    long delete(@Param("id") Long id);

    /**
     * 更新实体信息
     * @param personAuthRecord
     */
    @UpdateProvider(type = PersonAuthRecordMapperProvider.class, method = "update")
    long update(PersonAuthRecord personAuthRecord);

    /**
     * 更新实体信息，null值字段不更新
     * @param personAuthRecord
     * @return
     */
    @UpdateProvider(type = PersonAuthRecordMapperProvider.class, method = "updateIgnoreNull")
    long updateIgnoreNull(PersonAuthRecord personAuthRecord);

    class PersonAuthRecordMapperProvider {
        public String insert() {
            return new SQL(){{
                INSERT_INTO("person_auth_record");
                VALUES("name", "#{name}");
                VALUES("id_card", "#{idCard}");
                VALUES("certif_status", "#{certifStatus}");
                VALUES("req_status", "#{reqStatus}");
                VALUES("al_result_json", "#{alResultJson}");
                VALUES("create_date", "#{createDate}");
                VALUES("update_date", "#{updateDate}");
                VALUES("build_user_id", "#{buildUserId}");
            }}.toString();
        }

        public String update(PersonAuthRecord personAuthRecord) {
            return new SQL(){{
                UPDATE("person_auth_record");
                SET("`name` = #{name}");
                SET("`id_card` = #{idCard}");
                SET("`certif_status` = #{certifStatus}");
                SET("`req_status` = #{reqStatus}");
                SET("`al_result_json` = #{alResultJson}");
                SET("`create_date` = #{createDate}");
                SET("`update_date` = #{updateDate}");
                SET("`build_user_id` = #{buildUserId}");
                WHERE("`id` = #{id}");
            }}.toString();
        }

        public String updateIgnoreNull(PersonAuthRecord personAuthRecord) {
            return new SQL(){{
                UPDATE("person_auth_record");
                if (personAuthRecord.getName() != null) {
                    SET("`name` = #{name}");
                }
                if (personAuthRecord.getIdCard() != null) {
                    SET("`id_card` = #{idCard}");
                }
                if (personAuthRecord.getCertifStatus() != null) {
                    SET("`certif_status` = #{certifStatus}");
                }
                if (personAuthRecord.getReqStatus() != null) {
                    SET("`req_status` = #{reqStatus}");
                }
                if (personAuthRecord.getAlResultJson() != null) {
                    SET("`al_result_json` = #{alResultJson}");
                }
                if (personAuthRecord.getCreateDate() != null) {
                    SET("`create_date` = #{createDate}");
                }
                if (personAuthRecord.getUpdateDate() != null) {
                    SET("`update_date` = #{updateDate}");
                }
                if (personAuthRecord.getBuildUserId() != null) {
                    SET("`build_user_id` = #{buildUserId}");
                }
                WHERE("`id` = #{id}");
            }}.toString();
        }
    }

}