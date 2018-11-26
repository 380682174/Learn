package com.fish.learn.mongodb.dao;

import com.fish.learn.mongodb.bean.MongoTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author devin.jiang
 * @CreateDate 2018/11/26 15:23
 */
@Component
public class MongoTestDao {


        @Autowired
        private MongoTemplate mongoTemplate;

        /**
         * 创建对象
         */
        public void saveTest(MongoTest test) {
            mongoTemplate.save(test);
        }

        /**
         * 根据用户名查询对象
         * @return
         */
        public MongoTest findTestByName(String name) {
            Query query=new Query(Criteria.where("name").is(name));
            MongoTest mgt =  mongoTemplate.findOne(query , MongoTest.class);
            return mgt;
        }

        /**
         * 更新对象
         */
        public void updateTest(MongoTest test) {
            Query query=new Query(Criteria.where("id").is(test.getId()));
            Update update= new Update().set("age", test.getAge()).set("name", test.getName());
            //更新查询返回结果集的第一条
            mongoTemplate.updateFirst(query,update,MongoTest.class);
            //更新查询返回结果集的所有
            // mongoTemplate.updateMulti(query,update,TestEntity.class);
        }

        /**
         * 删除对象
         * @param id
         */
        public void deleteTestById(Integer id) {
            Query query=new Query(Criteria.where("id").is(id));
            mongoTemplate.remove(query,MongoTest.class);
        }

    public List<MongoTest> findList(List<Integer> ids) {
        Query query = new Query(Criteria.where("id").in(ids));
        return mongoTemplate.find(query,MongoTest.class);
    }

    public List<MongoTest> findList7(List<String> ids) {
        Query query = new Query(Criteria.where("title").in(ids));
        return mongoTemplate.find(query,MongoTest.class);
    }
}
