package priv.jesse.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Brand {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    /**
     * 品牌名称
     */
    @Column
    private String name;

    /**
     * 推荐理由
     */
    @Column
    private String reason;

    /**
     * 推荐人群
     */
    @Column
    private String people;

    @Column
    private Boolean isRecommended;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Boolean isRecommended) {
        isRecommended = isRecommended;
    }
}
