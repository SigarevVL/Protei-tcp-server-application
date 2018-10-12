package ru.protei.server.dao.interfaces;

import ru.protei.server.objects.Word;

import java.util.List;

public interface WordDao {

    int insert(Word word);

    int update(Word word);

    int delete(Word word);

    List<Word> select(Word word);

    List<Word> selectByMask(Word word);

}
