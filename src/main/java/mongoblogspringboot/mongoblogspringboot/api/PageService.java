package mongoblogspringboot.mongoblogspringboot.api;

import mongoblogspringboot.mongoblogspringboot.dto.Page;

import java.util.List;

public interface PageService {

    List<Page> findById(String id);


}
