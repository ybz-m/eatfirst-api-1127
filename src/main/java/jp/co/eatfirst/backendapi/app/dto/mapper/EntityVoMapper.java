package jp.co.eatfirst.backendapi.app.dto.mapper;

import java.util.List;

public interface EntityVoMapper<D, E> {

//    public E toEntity(D dto);

    public D toVo(E entity);

//    public List<E> toEntity(List<D> dtoList);

    public List<D> toVo(List<E> entityList);

}
