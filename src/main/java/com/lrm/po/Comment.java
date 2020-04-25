package com.lrm.po;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//评论类
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    //昵称
    private String nickname;
    private String email;
    //内容
    private String content;
    //图片
    private String avatar;

    //数据库里面的时间表的类型
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

   @ManyToOne
    private Blog blog;
    public Comment() {
    }

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComents = new ArrayList<>();

    //父类
    @ManyToOne
    private Comment parentComment;

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<Comment> getReplyComents() {
        return replyComents;
    }

    public void setReplyComents(List<Comment> replyComents) {
        this.replyComents = replyComents;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }

}
