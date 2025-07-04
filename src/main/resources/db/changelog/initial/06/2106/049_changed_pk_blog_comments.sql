ALTER TABLE blog_comments DROP CONSTRAINT blog_comments_pkey;
ALTER TABLE blog_comments DROP COLUMN id;
ALTER TABLE blog_comments ADD PRIMARY KEY (comment_id, blog_id);