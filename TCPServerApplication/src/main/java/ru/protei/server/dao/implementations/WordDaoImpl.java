package ru.protei.server.dao.implementations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.protei.server.dao.interfaces.WordDao;
import ru.protei.server.objects.Word;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

public class WordDaoImpl implements WordDao {

    private static final Logger log = LogManager.getLogger(WordDaoImpl.class);

    private static final String SQL_SELECT_WORD = "SELECT * FROM explanatory_dictionary WHERE word = ?";
    private static final String SQL_SELECT_WORD_BY_MASK = "SELECT * FROM explanatory_dictionary WHERE word LIKE ?";
    private static final String SQL_INSERT_WORD =
            "INSERT INTO explanatory_dictionary (word, explanation) VALUES (?, ?)";
    private static final String SQL_UPDATE_WORD = "UPDATE explanatory_dictionary SET explanation = ? WHERE word = ?";
    private static final String SQL_DELETE_WORD = "DELETE FROM explanatory_dictionary WHERE word = ?";

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int insert(Word word) {
        try {
            return jdbcTemplate.update(SQL_INSERT_WORD, new Object[]{word.getWord(), word.getWordExplanation()});
        } catch (Exception e) {
            log.error("INSERT error");
            log.error(e);
            return -1;
        }
    }

    @Override
    public int update(Word word) {
        try {
            return jdbcTemplate.update(SQL_UPDATE_WORD, new Object[]{word.getWordExplanation(), word.getWord()});
        } catch (UncategorizedSQLException e) {
            log.error("UPDATE error");
            log.error(e);
            return -1;
        }
    }

    @Override
    public int delete(Word word) {
        try {
            return jdbcTemplate.update(SQL_DELETE_WORD, word.getWord());
        } catch (UncategorizedSQLException e) {
            log.error("DELETE error");
            log.error(e);
            return -1;
        }
    }

    @Override
    public List<Word> select(Word word) {
        try {
            return jdbcTemplate.query(SQL_SELECT_WORD, new Object[]{word.getWord()}, new WordRowMapper());
        } catch (UncategorizedSQLException e) {
            log.error("SELECT error");
            log.error(e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Word> selectByMask(Word word) {
        try {
            return jdbcTemplate.query(SQL_SELECT_WORD_BY_MASK, new Object[]{word.getWord()}, new WordRowMapper());
        } catch (UncategorizedSQLException e) {
            log.error("SELECT_BY_MASK error");
            log.error(e);
            return Collections.emptyList();
        }

    }


}
