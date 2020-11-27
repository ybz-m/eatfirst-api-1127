package jp.co.eatfirst.backendapi.app.dto.mapper;

import java.util.List;

public interface FormDtoMapper <F, D> {

    public D toDto(F form);
    public List<D> toDto(List<F> formList);

}
