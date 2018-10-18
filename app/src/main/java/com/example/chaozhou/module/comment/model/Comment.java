package com.example.chaozhou.module.comment.model;

/**
 * 评论对象
 */
public class Comment {
    public String mContent; // 评论内容
    public User mCommentator; // 评论者

    public Comment(User mCommentator, String mContent) {
        this.mCommentator = mCommentator;
        this.mContent = mContent;

    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public User getmCommentator() {
        return mCommentator;
    }

    public void setmCommentator(User mCommentator) {
        this.mCommentator = mCommentator;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "mContent='" + mContent + '\'' +
                ", mCommentator=" + mCommentator +
                '}';
    }
}
