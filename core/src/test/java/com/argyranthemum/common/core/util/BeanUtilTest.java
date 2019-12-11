package com.argyranthemum.common.core.util;

import com.argyranthemum.common.core.pojo.Result;
import com.argyranthemum.common.core.util.enums.IncomeEnum;
import com.argyranthemum.common.core.util.enums.IncomeRoleEnum;
import com.argyranthemum.common.core.util.pojo.User;
import com.argyranthemum.common.core.util.pojo.UserPojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Created by estn on 16/11/12.
 */
public class BeanUtilTest extends TestCase {

    public void test001() {
        for (int i = 0; i < 1; i++) {
            Result result = HttpUtil.get("https://www.xbiquge6.com/90_90349/110136.html");
            if (result.succeed) {
                String data = result.getData();
                System.out.println(HtmlUtil.find(data, "<div id=\\\"content\\\">((.)+?)</div>", "<br/><br/>"));
            }
        }
    }

    public void testMergeList() throws Exception {
        List<User> users = Lists.newArrayList();
        User user = new User();
        user.setUsername("estn");
        user.setPassword("123456");
        users.add(user);

        User user1 = new User();
        user1.setUsername("jack");
        user1.setPassword("11111");
        users.add(user1);


        List<UserPojo> userPojos = BeanUtil.mergeList(users, UserPojo.class);

        assertNotNull(userPojos);
        assertEquals(userPojos.size(), users.size());

    }

    public void testMergeList1() throws Exception {
        List<User> users = Lists.newArrayList();
        User user = new User();
        user.setUsername("estn");
        user.setPassword("123456");
        users.add(user);

        User user1 = new User();
        user1.setUsername("jack");
        user1.setPassword("11111");
        users.add(user1);


        List<UserPojo> userPojos = BeanUtil.mergeList(users, UserPojo.class, new String[]{"username"});

        assertNotNull(userPojos);
        assertEquals(userPojos.size(), users.size());
        for (UserPojo userPojo : userPojos) {
            Assert.assertNull(userPojo.getUsername());
        }
    }

    public void testMerge() throws Exception {
        User user = new User();

        user.setId(1L);
        user.setUsername("estn");
        user.setPassword("123456");

        UserPojo userPojo = BeanUtil.merge(user, UserPojo.class);

        Assert.assertNotNull(userPojo);
        Assert.assertEquals(userPojo.getUsername(), user.getUsername());
        Assert.assertEquals(userPojo.getPassword(), user.getPassword());

    }


    public void testMergeIgnore() throws Exception {
        User user = new User();
        user.setUsername("estn");
        user.setPassword("123456");

        UserPojo userPojo = BeanUtil.merge(user, UserPojo.class, new String[]{"username"});

        Assert.assertNotNull(userPojo);
        Assert.assertNull(userPojo.getUsername());
        Assert.assertEquals(userPojo.getPassword(), user.getPassword());

    }

    public void testMerge1() throws Exception {
        User user1 = new User();
        user1.setUsername("estn");
        user1.setPassword("123456");


        User user2 = new User();
        user2.setUsername("abcd");
        user2.setPassword("11111");


        user2 = BeanUtil.merge(user1, user2);

        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);
        Assert.assertEquals(user2.getUsername(), user1.getUsername());
        Assert.assertEquals(user2.getPassword(), user1.getPassword());


        user1 = new User();
        user1.setUsername("estn");
        user1.setPassword(null);


        user2 = new User();
        user2.setUsername("abcd");
        user2.setPassword("11111");

        user2 = BeanUtil.merge(user1, user2);

        Assert.assertEquals(user2.getUsername(), user1.getUsername());
        Assert.assertNotEquals(user2.getPassword(), user1.getPassword());
    }

    public void testMergeIgnoreField() throws Exception {
        User user1 = new User();
        user1.setUsername("estn");
        user1.setPassword("123456");


        User user2 = new User();
        user2.setUsername("abcd");
        user2.setPassword("11111");


        user2 = BeanUtil.merge(user1, user2, new String[]{"username"});

        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);
        Assert.assertEquals(user2.getUsername(), "abcd");
        Assert.assertEquals(user2.getPassword(), user1.getPassword());


    }

    public void testConvert$Map() throws Exception {
        User user1 = new User();
        user1.setUsername("estn");
        user1.setPassword("123456");

        Map<String, Object> map = BeanUtil.convert$Map(user1);

        Assert.assertNotNull(map);
        Assert.assertEquals(map.size(), map.size());
        assertEquals(map.get("username"), user1.getUsername());
        assertEquals(map.get("password"), user1.getPassword());
    }

    public void testConvert$Bean() throws Exception {

        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "estn");
        map.put("password", "123456");
        map.put("home_location", "s");
        map.put("id", 1);
        map.put("create_time", "2017-01-01 12:12:12");

        User user = BeanUtil.convert$Bean(User.class, map);

        assertNotNull(user);
        assertEquals(user.getUsername(), map.get("username"));
        assertEquals(user.getPassword(), map.get("password"));
        assertTrue(user.getId() == 1L);
        System.out.println(user);

    }

    public void testConvert$BeanEnum() throws Exception {

        Map<String, Object> map = Maps.newHashMap();
        map.put("incomeRole", "FIRST");

        User user = BeanUtil.convert$Bean(User.class, map);

        assertNotNull(user);
        assertTrue(user.getIncomeRole().equals(IncomeRoleEnum.FIRST));
        System.out.println(user);

    }

    public void testConvert$BeanEnum2() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("incomeRole", 1);
        User user = BeanUtil.convert$Bean(User.class, map);
        assertNotNull(user);
        assertTrue(user.getIncomeRole().equals(IncomeRoleEnum.PLATFORM));
        System.out.println(user);

    }

    @Test
    public void testMergeEnum() {
        User user = new User();
        user.setUsername("say hello");
        user.setIncomeRole(IncomeRoleEnum.MERCHANT);


        UserPojo merge = BeanUtil.merge(user, UserPojo.class);

        System.out.println(merge);


    }


    public void testStringToLong() {
        UserPojo userPojo = new UserPojo();
        userPojo.setId("1");
        User merge = BeanUtil.merge(userPojo, User.class);
        assertTrue(merge.getId().equals(1L));
    }


    public void testEnum() {
        User user = new User();
        user.setIncomeRole(IncomeRoleEnum.MERCHANT);
        UserPojo pojo = BeanUtil.merge(user, UserPojo.class);
        Assert.assertTrue(pojo.getIncomeRole().equals(IncomeEnum.MERCHANT));
    }


    public void testSetProperty() {
        User user = new User();
        BeanUtil.setProperty(user, "username", "say hello");
        Assert.assertTrue(user.getUsername().equals("say hello"));
    }

    public void testInterge() {
        UserPojo pojo = new UserPojo();
        pojo.setAge(1);

        User user = BeanUtil.merge(pojo, User.class);
        Assert.assertTrue(pojo.getAge() == user.getAge());


        User _user = new User();
        _user.setAge(1);

        UserPojo userPojo = BeanUtil.merge(_user, UserPojo.class);
        Assert.assertTrue(_user.getAge() == userPojo.getAge());
    }

    public void testEnum2() {
        UserPojo pojo = new UserPojo();
        pojo.setIncomeRole2("PLATFORM");

        User user = BeanUtil.merge(pojo, User.class);
        Assert.assertEquals(IncomeRoleEnum.PLATFORM, user.getIncomeRole2());


        User _user = new User();
        _user.setIncomeRole2(IncomeRoleEnum.PLATFORM);
        UserPojo merge = BeanUtil.merge(_user, UserPojo.class);

        Assert.assertEquals("PLATFORM", merge.getIncomeRole2());

    }

    public void testBigNumber() {
        User user = new User();
        user.setIncome(new BigDecimal("1.0"));

        UserPojo pojo = BeanUtil.merge(user, UserPojo.class);

        Assert.assertTrue(user.getIncome().equals(new BigDecimal(pojo.getIncome())));

    }


}