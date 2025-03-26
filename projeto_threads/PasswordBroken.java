class PasswordBroken extends Thread {
    private static volatile boolean passwordFound = false;
    private String password;
    private int threadNumber;
    private int totalThreads;

    public PasswordBroken(String password, int threadNumber, int totalThreads) {
        this.password = password;
        this.threadNumber = threadNumber;
        this.totalThreads = totalThreads;
        this.setName("Thread " + threadNumber);
    }

    @Override
    public void run() {
        long start = (10000L / totalThreads) * threadNumber;
        long end;
        if (threadNumber == totalThreads - 1) {
            end = 10000L;
        } else {
            end = (10000L / totalThreads) * (threadNumber + 1);
        }

        for (long i = start; i < end; i++) {
            String attempt = String.format("%04d", i);

            synchronized (PasswordBroken.class) {
                if (passwordFound) {
                    return; // Sai da thread se a senha já foi encontrada
                }

                System.out.println(Thread.currentThread().getName() + " tentando a senha: " + attempt);

                if (attempt.equals(password)) {
                    passwordFound = true;
                    System.out.println("Senha encontrada: " + attempt + " pela " + Thread.currentThread().getName());
                    return; // Sai da thread após encontrar a senha
                }
            }
        }
    }
}