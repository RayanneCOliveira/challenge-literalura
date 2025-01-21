package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record  BookInfos(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorInfos> authorInfos,
        @JsonAlias("languages") ArrayList languages,
        @JsonAlias("download_count") int downloads
) {}
