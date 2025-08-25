package com.internaltools.demo1.factory;

import com.internaltools.demo1.entities.AccessRequests;
import com.internaltools.demo1.entities.DUsers;
import com.internaltools.demo1.entities.Tools;
import com.internaltools.demo1.entities.Categories;
import com.internaltools.demo1.entities.enums.Role;
import com.internaltools.demo1.entities.enums.Department;
import com.internaltools.demo1.entities.enums.Status;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InternalFactory {

    public static final List<DUsers> INTERNAL_USERS;
    private static final NameGenerator GENERATOR = new NameGenerator();
    private static final Random RANDOM = new Random();

    private static final int INTERNAL_USERS_MIN_SIZE = 5;
    private static final int INTERNAL_USERS_MAX_SIZE = 10;
    private static final int TOOLS_MIN_SIZE = 5;
    private static final int TOOLS_MAX_SIZE = 10;
    private static final int ACCESS_REQUESTS_MAX_PER_USER = 3;

    private static final List<String> VENDORS = List.of(
            "Microsoft",
            "GoogleAPI Map",
            "AirTable",
            "Oracle",
            "Intellij Enterprise",
            "Reflexion5",
            "Zoom",
            "Nagios",
            "Salesforce"
    );


    static {
        INTERNAL_USERS = generateDUsersList();
    }

    private InternalFactory() {}

    // ─── USERS ──────────────────────────────
    private static List<DUsers> generateDUsersList() {
        return Stream.generate(InternalFactory::generateRandomUser)
                .limit(RANDOM.nextInt(INTERNAL_USERS_MAX_SIZE - INTERNAL_USERS_MIN_SIZE + 1)
                        + INTERNAL_USERS_MIN_SIZE)
                .toList();
    }

    public static DUsers generateInternalAdmin() {
        return DUsers.builder()
                .name("Admin User")
                .email("admin@mail.com")
                .role(Role.ADMIN)
                .department(Department.ENGINEERING)
                .status(Status.ACTIVE)
                .hireDate(LocalDate.now().minusYears(5).toString())
                .build();
    }

    public static DUsers generateRandomUser() {
        Name name = GENERATOR.generateName();
        String fullName = name.getFirstName() + " " + name.getLastName();
        String email = (name.getFirstName() + "." + name.getLastName() + "@mail.com").toLowerCase();

        return DUsers.builder()
                .name(fullName)
                .email(email)
                .role(randomEnum(Role.class))
                .department(randomEnum(Department.class))
                .status(randomEnum(Status.class))
                .hireDate(LocalDate.now().minusDays(RANDOM.nextInt(365 * 5)).toString())
                .build();
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        T[] values = clazz.getEnumConstants();
        return values[RANDOM.nextInt(values.length)];
    }

    // ─── TOOLS ──────────────────────────────
    public static List<Categories> generateCategoriesList() {
        return IntStream.range(0, 5)
                .mapToObj(i -> Categories.builder()
                        .name("Category_id " + (i + 1))
                        .description("Description for Category " + (i + 1))
                        .build())
                .toList();
    }

    public static List<Tools> generateToolsListWithSavedCategories(List<Categories> savedCategories) {
        return IntStream.range(0, RANDOM.nextInt(TOOLS_MAX_SIZE - TOOLS_MIN_SIZE + 1) + TOOLS_MIN_SIZE)
                .mapToObj(i -> {
                    Categories category = savedCategories.get(RANDOM.nextInt(savedCategories.size()));
                    return Tools.builder()
                            .name("Tool " + (i + 1))
                            .description("Description for Tool " + (i + 1))
                            .monthlyCost(BigDecimal.valueOf(RANDOM.nextInt(900) + 100))
                            .activeUsersCount(RANDOM.nextInt(50) + 1)
                            .category(category)
                            .ownerDepartment(randomEnum(Tools.OwnerDepartment.class))
                            .status(randomEnum(Tools.Status.class))
                            .vendor(VENDORS.get(RANDOM.nextInt(VENDORS.size()))) // ✅ toujours rempli
                            .websiteUrl("https://www.example.com/tool" + (i + 1))
                            .build();
                }).toList();
    }




    // ─── ACCESS REQUESTS ──────────────────────────────
    public static List<AccessRequests> generateAccessRequests(List<DUsers> users, List<Tools> tools) {
        return users.stream()
                .flatMap(user -> Stream.generate(() -> generateRandomAccessRequest(user, tools))
                        .limit(RANDOM.nextInt(ACCESS_REQUESTS_MAX_PER_USER) + 1))
                .toList();
    }

    private static AccessRequests generateRandomAccessRequest(DUsers user, List<Tools> tools) {
        Tools tool = tools.get(RANDOM.nextInt(tools.size()));
        AccessRequests.Status status = randomEnum(AccessRequests.Status.class);
        String justification = "Automated request for " + tool.getName();

        AccessRequests request = AccessRequests.builder()
                .dusers(user)
                .tools(tool)
                .businessJustification(justification)
                .status(status)
                .requestedAt(LocalDateTime.now().minusDays(RANDOM.nextInt(180)))
                .build();

        if (status != AccessRequests.Status.PENDING) {
            request.setProcessedAt(LocalDateTime.now().minusDays(RANDOM.nextInt(90)));
            request.setProcessedBy(user); // pour simplifier
            request.setProcessingNotes("Automated processing");
        }

        return request;
    }
}
