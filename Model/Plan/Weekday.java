package mealplanner.Model.Plan;

public enum Weekday {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String nameWeekday;

    private Weekday(String nameWeekday) {
        this.nameWeekday = nameWeekday;
    }

    public Weekday getWeekday(String nameWeekday) {
        for (Weekday weekdays : Weekday.values()) {
            if (weekdays.nameWeekday.equalsIgnoreCase(nameWeekday))
                return weekdays;
        }
        return Weekday.MONDAY;
    }

    public String getNameWeekday() {
        return nameWeekday;
    }
}
