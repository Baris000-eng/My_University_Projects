#include <stdio.h>
int secret_pin=12345;

void transfer_money() {
        printf("$1000 was transferred to your account.\n");
}

void check_password() {
        char username[100];
        int check_pin;

        printf("Enter username:\n");
        scanf("%99[^\n]s",username);

        printf("Your username was: %s\n",username);

        printf("Enter pin:\n");
        scanf("%d",&check_pin);

        if (check_pin==secret_pin)
        {
                printf("You entered the right pin!\n");
                transfer_money();
        }
        else
                printf("Invaild pin. bye.\n");
}




int main() {
        check_password();
        return 0;
}
                               