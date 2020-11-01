package io.github.gilsomanfredi.cadastropessoa.repository.template;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class PostgresRepositoryTemplate {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public <T> T queryForObject(String sql, Map<String, Object> parameters, RowMapper<T> rowMapper) {

        try {

            return getJdbcTemplate().queryForObject(sql, parameters, rowMapper);

        } catch (EmptyResultDataAccessException var5) {

            return null;
        }
    }

    public <T> T queryForObject(String sql, Map<String, Object> parameters, Class<T> clazz) {

        try {

            return getJdbcTemplate().queryForObject(sql, parameters, clazz);

        } catch (EmptyResultDataAccessException var5) {

            return null;
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {

        return query(sql, new HashMap<>(), rowMapper);
    }

    public <T> List<T> query(String sql, Map<String, Object> parameters, RowMapper<T> rowMapper) {

        return getJdbcTemplate().query(sql, parameters, rowMapper);
    }

    public <T> Page<T> query(String sql, RowMapper<T> rowMapper, Pageable pageable) {

        return query(sql, new HashMap<>(), rowMapper, pageable);
    }

    public <T> Page<T> query(String sql, Map<String, Object> parameters, RowMapper<T> rowMapper, Pageable pageable) {

        String sqlPaginacao;
        Long quantidade = count(sql, parameters);

        if (sql != null && pageable != null) {
            int limit = pageable.getPageSize();
            int offset = (pageable.getPageNumber()) * pageable.getPageSize();
            sqlPaginacao = String.format("%s limit %d offset %d", sql, limit, offset);
        } else {
            sqlPaginacao = sql;
        }

        List<T> list = getJdbcTemplate().query(sqlPaginacao, parameters, rowMapper);

        return new PageImpl<>(list, pageable, quantidade);
    }

    public Long count(String sql, Map<String, Object> parameters) {

        String sqlCount = String.format("SELECT COUNT(*) FROM ( %s ) AS temp", sql);

        return getJdbcTemplate().queryForObject(sqlCount, parameters, Long.class);
    }

    public Long insert(String sql, Map<String, Object> parameters, Class<?> clazz) {

        Long generatedId = null;

        Optional<Field> findFieldId = findFieldId(clazz);

        if (findFieldId.isPresent()) {

            generatedId = insert(sql, parameters, findFieldId.get().getName());

        } else {

            update(sql, parameters);
        }

        return generatedId;
    }

    private <T> Optional<Field> findFieldId(Class<?> clazz) {

        List<Field> declaredFields = new ArrayList<>();

        while (clazz != null) {

            declaredFields.addAll(Arrays.asList(clazz.getDeclaredFields()));

            clazz = clazz.getSuperclass();
        }

        return declaredFields.stream()
                .filter(field -> Stream.of(field.getDeclaredAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().getName().equals(Id.class.getName())))
                .findFirst();
    }

    public Long insert(String sql, Map<String, Object> parameters, String generatedId) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource(parameters);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(sql, parameterSource, keyHolder, new String[]{generatedId});

        return this.getKeyHolder(keyHolder);
    }

    public Long getKeyHolder(KeyHolder keyHolder) {

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    public void update(String sql, Map<String, Object> parameters) {

        MapSqlParameterSource parameterSource = new MapSqlParameterSource(parameters);

        this.getJdbcTemplate().update(sql, parameterSource);
    }

    public void delete(String sql, Map<String, Object> parameters) {

        this.update(sql, parameters);
    }
}
