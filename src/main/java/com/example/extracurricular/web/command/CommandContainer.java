package com.example.extracurricular.web.command;

import java.util.HashMap;
import java.util.Map;

public final class CommandContainer {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("", new HomeCommand());
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("settings", new SettingsCommand());
        commands.put("users", new UsersCommand());
        commands.put("profile", new ProfileCommand());
        commands.put("courses", new CoursesCommand());
        commands.put("createcourse", new CreateCourseCommand());
        commands.put("editcourse", new EditCourseCommand());
    }

    public static Command getCommand(String key) {
        if (commands.containsKey(key)) {
            return commands.get(key);
        }
        return new WrongCommand();
    }

    private CommandContainer() {
    }
}
