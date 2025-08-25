package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.AccessRequests;
import com.internaltools.demo1.entities.DUsers;
import com.internaltools.demo1.entities.Tools;
import com.internaltools.demo1.entities.Categories;
import com.internaltools.demo1.factory.InternalFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class DbInit {

    private final DUsersRepository userRepository;
    private final ToolsRepository toolsRepository;
    private final CategoriesRepository categoriesRepository;
    private final AccessRequestsRepository accessRequestsRepository;

    public DbInit(DUsersRepository userRepository,
                  ToolsRepository toolsRepository,
                  CategoriesRepository categoriesRepository,
                  AccessRequestsRepository accessRequestsRepository) {
        this.userRepository = userRepository;
        this.toolsRepository = toolsRepository;
        this.categoriesRepository = categoriesRepository;
        this.accessRequestsRepository = accessRequestsRepository;
    }

    @PostConstruct
    public void init() {
        // 1️⃣ Users
        List<DUsers> users = InternalFactory.INTERNAL_USERS;
        users.forEach(userRepository::save);
        userRepository.save(InternalFactory.generateInternalAdmin());

        // 2️⃣ Categories -> à sauver **avant** les Tools
        List<Categories> categories = InternalFactory.generateCategoriesList();
        categories.forEach(categoriesRepository::save);

        // 3️⃣ Tools -> maintenant que les catégories sont en DB
        List<Tools> tools = InternalFactory.generateToolsListWithSavedCategories(categories);
        tools.forEach(toolsRepository::save);

        // 4️⃣ Access Requests
        List<AccessRequests> requests = InternalFactory.generateAccessRequests(users, tools);
        requests.forEach(accessRequestsRepository::save);
    }

}
