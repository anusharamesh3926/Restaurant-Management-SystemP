import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Restaurant {
    private String name;
    private String address;
    private List<MenuItem> menuItems;
    private List<Employee> employees;
    private List<Reservation> reservations;

    public Restaurant(String name) {
        this(name, "Unknown Address");
    }

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
        this.menuItems = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public void addMenuItem(String name, double price, MenuCategory category) {
        menuItems.add(new MenuItem(name, price, category));
    }

    public void addMenuItems(MenuItem... items) {
        for (MenuItem item : items) {
            menuItems.add(item);
        }
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Reservation createReservation(Customer customer, LocalDateTime dateTime,
            int partySize, TableType tableType)
            throws ReservationException {

        if (partySize <= 0) {
            throw new ReservationException("Party size must be positive");
        }

        if (partySize > 20) {
            throw new ReservationException("Party size exceeds maximum capacity");
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new ReservationException("Cannot make reservation in the past");
        }

        String reservationId = "RES" + (reservations.size() + 1);
        Reservation reservation = new Reservation(reservationId, customer, dateTime,
                partySize, tableType);
        reservations.add(reservation);
        return reservation;
    }

    public void displayInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Restaurant: ").append(name).append("\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Menu Items: ").append(menuItems.size()).append("\n");
        sb.append("Employees: ").append(employees.size());
        System.out.println(sb.toString());
    }

    public void displayEmployees() {
        for (Employee emp : employees) {
            emp.displayInfo();
        }
    }

    public void advancedStreamOperations() {

        System.out.println("\n=== Advanced Streams ===");

        menuItems.stream()
                .filter(MenuItem::isExpensive)
                .forEach(System.out::println);

        menuItems.stream()
                .sorted(Comparator.comparing(MenuItem::price))
                .forEach(System.out::println);

        menuItems.stream()
                .map(MenuItem::category)
                .distinct()
                .forEach(System.out::println);

        menuItems.stream()
                .limit(2)
                .forEach(System.out::println);
    }

    public void collectorExamples() {

        System.out.println("\n=== Collectors ===");

        var grouped = menuItems.stream()
                .collect(Collectors.groupingBy(MenuItem::category));

        System.out.println(grouped);

        var partitioned = menuItems.stream()
                .collect(Collectors.partitioningBy(MenuItem::isExpensive));

        System.out.println(partitioned);

        var map = menuItems.stream()
                .collect(Collectors.toMap(
                        MenuItem::name,
                        MenuItem::price));

        System.out.println(map);
    }

    public void terminalOperations() {

        System.out.println("\n=== Terminal Ops ===");

        System.out.println("Count: " + menuItems.stream().count());

        menuItems.stream()
                .max(Comparator.comparing(MenuItem::price))
                .ifPresent(System.out::println);

        menuItems.stream()
                .findFirst()
                .ifPresent(System.out::println);

        boolean anyExpensive = menuItems.stream()
                .anyMatch(MenuItem::isExpensive);

        System.out.println("Any expensive: " + anyExpensive);
    }

    public Optional<MenuItem> findExpensiveItem() {
        return menuItems.stream()
                .filter(MenuItem::isExpensive)
                .findFirst();
    }

    public List<MenuItem> getMenuItems() {
        return new ArrayList<>(menuItems);
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}