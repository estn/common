/**
 * Copyright  2018  Pemass
 * All Right Reserved.
 */
package com.argyranthemum.common.jpa.base;

import com.argyranthemum.common.domain.enumeration.AvailableEnum;
import com.argyranthemum.common.domain.pojo.DomainCursor;
import com.argyranthemum.common.domain.pojo.DomainPage;
import com.argyranthemum.common.jpa.ApplicationTests;
import com.argyranthemum.common.jpa.condition.*;
import com.argyranthemum.common.jpa.entity.Article;
import com.argyranthemum.common.jpa.repo.ArticleRepository;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description: Test BaseRepositoryImplTest
 * @Author: estn.zuo
 * @CreateTime: 2018-12-24 18:04
 */
public class BaseRepositoryImplTest extends ApplicationTests {

    @Resource
    private ArticleRepository articleRepository;

    @Test
    public void save() {
        Article article = new Article();
        article.setName("test");

        article = articleRepository.save(article);

        Assert.assertNotNull(article);
        Assert.assertNotNull(article.getAvailable());
        Assert.assertNotNull(article.getCreateTime());
        Assert.assertNotNull(article.getUpdateTime());
        Assert.assertNotNull(article.getUuid());
        Assert.assertEquals("test", article.getName());
    }

    @Test
    public void findById() {
        Article article = new Article();
        article.setName("test");
        article = articleRepository.save(article);

        Article article1 = articleRepository.findById(article.getId()).get();

        Assert.assertNotNull(article1);
        Assert.assertNotNull(article1.getAvailable());
        Assert.assertNotNull(article1.getCreateTime());
        Assert.assertNotNull(article1.getUpdateTime());
        Assert.assertNotNull(article1.getUuid());
        Assert.assertEquals(article.getId().longValue(), article1.getId().longValue());
        Assert.assertEquals("test", article1.getName());
    }

    @Test
    public void existsById() {

        Article article = new Article();
        article.setName("test");
        article = articleRepository.save(article);

        boolean exists = articleRepository.existsById(article.getId());
        Assert.assertTrue(exists);

        exists = articleRepository.existsById(article.getId() * -1);
        Assert.assertFalse(exists);
    }

    @Test
    public void count() {
        articleRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            articleRepository.save(article);
        }

        long count = articleRepository.count();
        Assert.assertEquals(10, count);
    }

    @Test
    public void deleteById() {
        Article article = new Article();
        article.setName("test");
        article = articleRepository.save(article);

        articleRepository.deleteById(article.getId());

        Optional<Article> optional = articleRepository.findById(article.getId());
        Assert.assertFalse(optional.isPresent());


        SQL sql = SQLBuilder.instance()
                .available(false)
                .wheres(new Where("id", article.getId()))
                .build();

        List<Article> select = articleRepository.select(sql);
        Assert.assertNotNull(select);
        Assert.assertEquals(1, select.size());

        Article article1 = select.get(0);
        Assert.assertEquals(article.getId().longValue(), article1.getId().longValue());
        Assert.assertTrue(AvailableEnum.UNAVAILABLE.equals(article1.getAvailable()));
    }

    @Test
    public void delete() {
        Article article = new Article();
        article.setName("test");
        article = articleRepository.save(article);

        articleRepository.delete(article);

        Optional<Article> optional = articleRepository.findById(article.getId());
        Assert.assertFalse(optional.isPresent());

    }

    @Test
    public void deleteAll() {
        articleRepository.deleteAll();

        Article article = new Article();
        article.setName("test");
        article = articleRepository.save(article);

        articleRepository.deleteAll(Lists.newArrayList(article));

        Optional<Article> optional = articleRepository.findById(article.getId());
        Assert.assertFalse(optional.isPresent());
    }

    @Test
    public void deleteAll1() {
        Article article = new Article();
        article.setName("test");
        articleRepository.save(article);

        articleRepository.deleteAll();

        SQL sql = SQLBuilder.instance()
                .build();

        List<Article> articles = articleRepository.select(sql);
        Assert.assertEquals(0, articles.size());
    }

    @Test
    public void update() {
        Article article = new Article();
        article.setName("test");
        article = articleRepository.save(article);

        article.setName("hello");
        articleRepository.update(article);

        Article article1 = articleRepository.findById(article.getId()).get();
        Assert.assertNotNull(article1);
        Assert.assertEquals("hello", article1.getName());
    }

    @Test
    public void select() {

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            articleRepository.save(article);
        }

        SQL sql = SQLBuilder.instance()
                .where("name", Operation.ALL_LIKE, "test")
                .build();

        List<Article> articles = articleRepository.select(sql);
        Assert.assertEquals(10, articles.size());

        SQL sql1 = SQLBuilder.instance()
                .where("id", 10L).build();
        List<Article> articles1 = articleRepository.select(sql1);
        Assert.assertEquals(1, articles1.size());
    }

    @Test
    public void selectByPage() {

        articleRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            articleRepository.save(article);
        }

        SQL sql = SQLBuilder.instance()
                .build();

        DomainPage<Article> domainPage = articleRepository.selectByPage(sql, 0, 2);
        Assert.assertNotNull(domainPage);
        Assert.assertEquals(10, (int) domainPage.getTotalCount());
        Assert.assertEquals(2, domainPage.getDomains().size());
        Assert.assertEquals(5, (int) domainPage.getPageCount());
    }

    @Test
    public void selectByCursor() {
        articleRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            articleRepository.save(article);
        }

        SQL sql = SQLBuilder.instance()
                .build();

        DomainCursor<Article> cursor = articleRepository.selectByCursor(sql, 0, 3);
        Assert.assertNotNull(cursor);
        Assert.assertEquals(1, (int) cursor.getNextCursor());
        Assert.assertEquals(3, cursor.getList().size());

        cursor = articleRepository.selectByCursor(sql, 3, 3);
        Assert.assertNotNull(cursor);
        Assert.assertEquals(DomainCursor.END_CURSOR, cursor.getNextCursor());
        Assert.assertEquals(1, cursor.getList().size());
    }

    @Test
    public void selectByPageWithOrderBy() {

        articleRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            articleRepository.save(article);
        }

        SQL sql = SQLBuilder.instance()
                .order("name", OrderBy.DESC)
                .build();

        DomainPage<Article> domainPage = articleRepository.selectByPage(sql, 0, 1);
        Assert.assertNotNull(domainPage);
        Assert.assertEquals("test9", domainPage.getDomains().get(0).getName());
    }

    @Transactional
    @Test
    public void refresh() {
        Article article = new Article();
        article.setName("test");
        article = articleRepository.save(article);

        article = articleRepository.findById(article.getId()).get();

        article.setName("hello");
        Article article1 = articleRepository.refresh(article);
    }

    @Test
    public void findAll() {
        articleRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            articleRepository.save(article);
        }

        Iterable<Article> all = articleRepository.findAll();

        List<Article> articles = Lists.newArrayList();
        for (Article article : all) {
            articles.add(article);
        }

        Assert.assertEquals(10, articles.size());
    }

    @Test
    public void findAllById() {
        articleRepository.deleteAll();

        List<Long> ids = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            article = articleRepository.save(article);
            ids.add(article.getId());
        }

        ids = ids.subList(0, 5);

        Iterable<Article> allById = articleRepository.findAllById(ids);

        List<Article> articles = Lists.newArrayList();
        for (Article article : allById) {
            articles.add(article);
        }

        Assert.assertEquals(5, articles.size());
    }

    @Test
    public void saveAllById() {
        articleRepository.deleteAll();

        List<Article> articles = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setName("test" + i);
            articles.add(article);
        }

        Iterable<Article> articles1 = articleRepository.saveAll(articles);
        for (Article article : articles1) {
            Assert.assertNotNull(article.getId());
        }

        long count = articleRepository.count();
        Assert.assertEquals(10, count);

    }

}