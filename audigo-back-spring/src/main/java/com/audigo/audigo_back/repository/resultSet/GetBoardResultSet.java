package com.audigo.audigo_back.repository.resultSet;

public interface GetBoardResultSet {
    Integer getBIdx();

    String getTitle();

    String getContent();

    String getWriteDatetime();

    String getWriterEmail();

    String getWriterNickname();

    String getWriterProfileImage();
}
