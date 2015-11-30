package learn.english.cofriends.reades;

final class Modules {
    static Object[] list(MainApplication app) {
        return new Object[]{
                new AppModule(app)
        };
    }

    private Modules() {
        // No instances.
    }
}
