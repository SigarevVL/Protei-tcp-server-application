package ru.protei.server.dao.implementations;

import org.springframework.jdbc.core.RowMapper;
import ru.protei.server.objects.Word;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class WordRowMapper implements RowMapper<Word> {
    @Override
    public Word mapRow(ResultSet resultSet, int i) throws SQLException {
        Word word = new Word();
        word.setWord(resultSet.getString("word"));
        word.setWordExplanation(resultSet.getString("explanation"));
        return word;
    }
}
