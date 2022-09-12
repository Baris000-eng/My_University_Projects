//LUTFU and BARIS


#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdbool.h>
#include <ctype.h>


#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <termios.h>            //termios, TCSANOW, ECHO, ICANON
#include <string.h>
#include <stdbool.h>
#include <errno.h>
#include <signal.h>
#include <string.h>



const char * sysname = "seashell";

enum return_codes {
	SUCCESS = 0,
	EXIT = 1,
	UNKNOWN = 2,
};
struct command_t {
	char *name;
	bool background;
	bool auto_complete;
	int arg_count;
	char **args;
	char *redirects[3]; // in/out redirection
	struct command_t *next; // for piping
};
/**
 * Prints a command struct
 * @param struct command_t *
 */
void print_command(struct command_t * command)
{
	int i=0;
	printf("Command: <%s>\n", command->name);
	printf("\tIs Background: %s\n", command->background?"yes":"no");
	printf("\tNeeds Auto-complete: %s\n", command->auto_complete?"yes":"no");
	printf("\tRedirects:\n");
	for (i=0;i<3;i++)
		printf("\t\t%d: %s\n", i, command->redirects[i]?command->redirects[i]:"N/A");
	printf("\tArguments (%d):\n", command->arg_count);
	for (i=0;i<command->arg_count;++i)
		printf("\t\tArg %d: %s\n", i, command->args[i]);
	if (command->next)
	{
		printf("\tPiped to:\n");
		print_command(command->next);
	}


}
/**
 * Release allocated memory of a command
 * @param  command [description]
 * @return         [description]
 */
int free_command(struct command_t *command)
{
	if (command->arg_count)
	{
		for (int i=0; i<command->arg_count; ++i)
			free(command->args[i]);
		free(command->args);
	}
	for (int i=0;i<3;++i)
		if (command->redirects[i])
			free(command->redirects[i]);
	if (command->next)
	{
		free_command(command->next);
		command->next=NULL;
	}
	free(command->name);
	free(command);
	return 0;
}
/**
 * Show the command prompt
 * @return [description]
 */
int show_prompt()
{
	char cwd[1024], hostname[1024];
    gethostname(hostname, sizeof(hostname));
	(cwd, sizeof(cwd));
	printf("%s@%s:%s %s$ ", getenv("USER"), hostname, cwd, sysname);
	return 0;
}
/**
 * Parse a command string into a command struct
 * @param  buf     [description]
 * @param  command [description]
 * @return         0
 */
int parse_command(char *buf, struct command_t *command)
{
	const char *splitters=" \t"; // split at whitespace
	int index, len;
	len=strlen(buf);
	while (len>0 && strchr(splitters, buf[0])!=NULL) // trim left whitespace
	{
		buf++;
		len--;
	}
	while (len>0 && strchr(splitters, buf[len-1])!=NULL)
		buf[--len]=0; // trim right whitespace

	if (len>0 && buf[len-1]=='?') // auto-complete
		command->auto_complete=true;
	if (len>0 && buf[len-1]=='&') // background
		command->background=true;

	char *pch = strtok(buf, splitters);
	command->name=(char *)malloc(strlen(pch)+1);
	if (pch==NULL)
		command->name[0]=0;
	else
		strcpy(command->name, pch);

	command->args=(char **)malloc(sizeof(char *));

	int redirect_index;
	int arg_index=0;
	char temp_buf[1024], *arg;
	while (1)
	{
		// tokenize input on splitters
		pch = strtok(NULL, splitters);
		if (!pch) break;
		arg=temp_buf;
		strcpy(arg, pch);
		len=strlen(arg);

		if (len==0) continue; // empty arg, go for next
		while (len>0 && strchr(splitters, arg[0])!=NULL) // trim left whitespace
		{
			arg++;
			len--;
		}
		while (len>0 && strchr(splitters, arg[len-1])!=NULL) arg[--len]=0; // trim right whitespace
		if (len==0) continue; // empty arg, go for next

		// piping to another command
		if (strcmp(arg, "|")==0)
		{
			struct command_t *c=malloc(sizeof(struct command_t));
			int l=strlen(pch);
			pch[l]=splitters[0]; // restore strtok termination
			index=1;
			while (pch[index]==' ' || pch[index]=='\t') index++; // skip whitespaces

			parse_command(pch+index, c);
			pch[l]=0; // put back strtok termination
			command->next=c;
			continue;
		}

		// background process
		if (strcmp(arg, "&")==0)
			continue; // handled before

		// handle input redirection
		redirect_index=-1;
		if (arg[0]=='<')
			redirect_index=0;
		if (arg[0]=='>')
		{
			if (len>1 && arg[1]=='>')
			{
				redirect_index=2;
				arg++;
				len--;
			}
			else redirect_index=1;
		}
		if (redirect_index != -1)
		{
			command->redirects[redirect_index]=malloc(len);
			strcpy(command->redirects[redirect_index], arg+1);
			continue;
		}

		// normal arguments
		if (len>2 && ((arg[0]=='"' && arg[len-1]=='"')
			|| (arg[0]=='\'' && arg[len-1]=='\''))) // quote wrapped arg
		{
			arg[--len]=0;
			arg++;
		}
		command->args=(char **)realloc(command->args, sizeof(char *)*(arg_index+1));
		command->args[arg_index]=(char *)malloc(len+1);
		strcpy(command->args[arg_index++], arg);
	}
	command->arg_count=arg_index;
	return 0;
}
void prompt_backspace()
{
	putchar(8); // go back 1
	putchar(' '); // write empty over
	putchar(8); // go back 1 again
}
/**
 * Prompt a command from the user
 * @param  buf      [description]
 * @param  buf_size [description]
 * @return          [description]
 */
int prompt(struct command_t *command)
{
	int index=0;
	char c;
	char buf[4096];
	static char oldbuf[4096];

    // tcgetattr gets the parameters of the current terminal
    // STDIN_FILENO will tell tcgetattr that it should write the settings
    // of stdin to oldt
    static struct termios backup_termios, new_termios;
    tcgetattr(STDIN_FILENO, &backup_termios);
    new_termios = backup_termios;
    // ICANON normally takes care that one line at a time will be processed
    // that means it will return if it sees a "\n" or an EOF or an EOL
    new_termios.c_lflag &= ~(ICANON | ECHO); // Also disable automatic echo. We manually echo each char.
    // Those new settings will be set to STDIN
    // TCSANOW tells tcsetattr to change attributes immediately.
    tcsetattr(STDIN_FILENO, TCSANOW, &new_termios);


    //FIXME: backspace is applied before printing chars
	show_prompt();
	int multicode_state=0;
	buf[0]=0;
  	while (1)
  	{
		c=getchar();
		// printf("Keycode: %u\n", c); // DEBUG: uncomment for debugging

		if (c==9) // handle tab
		{
			buf[index++]='?'; // autocomplete
			break;
		}

		if (c==127) // handle backspace
		{
			if (index>0)
			{
				prompt_backspace();
				index--;
			}
			continue;
		}
		if (c==27 && multicode_state==0) // handle multi-code keys
		{
			multicode_state=1;
			continue;
		}
		if (c==91 && multicode_state==1)
		{
			multicode_state=2;
			continue;
		}
		if (c==65 && multicode_state==2) // up arrow
		{
			int i;
			while (index>0)
			{
				prompt_backspace();
				index--;
			}
			for (i=0;oldbuf[i];++i)
			{
				putchar(oldbuf[i]);
				buf[i]=oldbuf[i];
			}
			index=i;
			continue;
		}
		else
			multicode_state=0;

		putchar(c); // echo the character
		buf[index++]=c;
		if (index>=sizeof(buf)-1) break;
		if (c=='\n') // enter key
			break;
		if (c==4) // Ctrl+D
			return EXIT;
  	}
  	if (index>0 && buf[index-1]=='\n') // trim newline from the end
  		index--;
  	buf[index++]=0; // null terminate string

  	strcpy(oldbuf, buf);

  	parse_command(buf, command);

  	// print_command(command); // DEBUG: uncomment for debugging

    // restore the old settings
    tcsetattr(STDIN_FILENO, TCSANOW, &backup_termios);
  	return SUCCESS;
}
int process_command(struct command_t *command);
int main()
{
	while (1)
	{
		struct command_t *command=malloc(sizeof(struct command_t));
		memset(command, 0, sizeof(struct command_t)); // set all bytes to 0

		int code;
		code = prompt(command);
		if (code==EXIT) break;

		code = process_command(command);
		if (code==EXIT) break;

		free_command(command);
	}

	printf("\n");
	return 0;
}

//These are the functions for the part3 -----------------------------------------------------------------------------------
void red () { //use this for printing red
  		printf("\033[1;31m");   
	}                                  
	void green () {  //use this for printing green
  		printf("\033[1;32m");
	}
	
	void blue () {   //use this for printing blue
  		printf("\033[1;34m");
	}
	
	void reset () {  //use this for reseting color to default color
  		printf("\033[0m");
	}	
//These are the functions for the part3 -----------------------------------------------------------------------------------

	
		
int checker(char* line, char* word){
	
	int count =0;
	for( int i = 0 ; i < strlen(line)-strlen(word) ; i++){
		int valid =1;
		
		if(i!=0){
			if( isalpha(line[i-1] )){
				valid =0;
			}
		}
		
		if(i!=strlen(line)-strlen(word)){
			if ( isalpha(line[ i + strlen(word)])){
				valid =0;
			}
		}
		
		for(int j = 0 ; j<strlen(word) ; j++){
			if(toupper(line[i+j])!=toupper(word[j])){	
				valid = 0;
			}
			
		
		
		}
		if(valid==1){
			count++;
		}
}
	return count;


}
	
	
	//These are the functions for the part3 -----------------------------------------------------------------------------------
	
	

int process_command(struct command_t *command)
{
	int r;
	if (strcmp(command->name, "")==0) return SUCCESS;

	if (strcmp(command->name, "exit")==0)
		return EXIT;

	if (strcmp(command->name, "cd")==0)
	{
		if (command->arg_count > 0)
		{
			r=chdir(command->args[0]); 
			if (r==-1)
				printf("-%s: %s: %s\n", sysname, command->name, strerror(errno));
			
		}
	}
	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	
	if (strcmp(command->name, "shortdir")==0){
		if((strcmp(command->args[0], "set"))==0){ 
			char currentDir[200];//get the current working directory
			char buf[200];
			getcwd(buf,1024);
			strcpy(currentDir,buf);
			char *name = strrchr(currentDir, '/');   //get the location of the last /
			*name++;
			printf("%s\n", name); 
			char allias[50];
			strcpy(allias,command->args[1]);
			printf("%s is set as an alias for %s\n",allias,currentDir);
			strcat(currentDir,"/");  
			pid_t pid;
			pid = fork();	
			if(pid == 0) {
				  FILE *file_ptr = fopen("~/.bashrc", "w");  //open the file that will be written on 
				  fseek(file_ptr, 0, SEEK_END);  //go to the end of the file
				  fprintf(file_ptr,"alias %s= cd %s\n",command->args[1],currentDir);  //write on the file
				  fclose(file_ptr); //close the file
				//echo 'export APP=/opt/tinyos-2.x/apps' >> ~/.bashrc
				execlp("/usr/bin/echo","/usr/bin/echo", "echo 'export APP=/opt/tinyos-2.x/apps' >> ~/.bashrc", (char *)NULL);  
					}
				
			   else {
			      waitpid(pid, NULL, 0);	
			  }
			  remove("currentDir");
		}
		if((strcmp(command->args[0], "jump"))==0){
			chdir("currentDir");   //change the direcory
		}
		if((strcmp(command->args[0], "del"))==0){
			  char *aliasArgs[] = {"unalias","command->args[1]",NULL};   
			  execv(aliasArgs[0], aliasArgs); //unalias command execution
		}
		if((strcmp(command->args[0], "clear"))==0){
			 char *aliasArgs2[] = {"unalias","-a",NULL};   
			  execv(aliasArgs2[0], aliasArgs2); //unalias -a command execution, -a deletes all the aliases
		}
		if((strcmp(command->args[0], "list"))==0){
			 char *aliasArgs3[] = {"alias",NULL};   
			  execv(aliasArgs3[0], aliasArgs3); // call alias command to list all.
		}
	}
	
	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2	// Part 2
	
	
int check(char *s,char *w)
{
    int n,a[1000],i,j,k=0,l,found=0,t=0;
 
    for(i=0;s[i];i++)
    {
    	if(s[i]==' ')
    	{
    		a[k++]=i;
		}
	}
	a[k++]=i;
	j=0;
	for(i=0;i<k;i++)
	{
		n=a[i]-j;
		if(n==strlen(w))
		{
			t=0;
			for(l=0;w[l];l++)
			{
				if(s[l+j]==w[l])
				{
					t++;
				}
			}
			if(t==strlen(w))
		    {
				found++;
				return 1;			 
		    }
		}
	
		j=a[i]+1;
	}
	 
 	return 0;
	
}
	
		
	
	// Part 3 	// Part 3		// Part 3 		// Part 3	// Part 3	// Part 3	// Part 3	// Part 3	// Part 3	// Part 3	// Part 3
	if (strcmp(command->name, "highlight")==0){
		pid_t pid_3=fork();
		if (pid_3==0){// child executing
				char string[500];
				char *token;
				FILE *fileptr;
    				fileptr = fopen(command->args[2], "r");    //open the first file
				while(fgets(string,500,fileptr)!=NULL){  //while there are lines
					if(checker(string,command->args[0])){
					token = strtok(string," ,.;:!");
					while(token!=NULL){
					if(strcasecmp(command->args[0],token)==0){
						
						if((strcasecmp(command->args[1],"r"))==0){ //call the helper function red if the arg[1] is r
							red();
						}
						else if((strcasecmp(command->args[1],"g"))==0){  //call the helper function green if the arg[1] is g
							green();
						}
						else if((strcasecmp(command->args[1],"b"))==0){  //call the helper function blue if the arg[1] is b
							blue();
						}
						int a = strlen(token);
						if(token[a-1]=='\n'){
							printf("%s",token);
						}
						else{
							printf("%s ",token);
						}	
					}
					else{
						reset();   //call the reset function to return to the default color
						int a = strlen(token);
						if(token[a-1]=='\n'){
						printf("%s",token);
						}
						else{
							printf("%s ",token);
						}
					}
					char delimit[]=" ,.;:";
					token=strtok(NULL,delimit);
					}
					}
				}
				fclose(fileptr);
			}
		else{
			wait(0);
		}
	}//End of part 3	//End of part 3	//End of part 3	//End of part 3	//End of part 3	//End of part 3	//End of part 3	//End of part 3		
	
	
	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	//Part 4	
	if(strcmp(command->name,"goodMorning")==0){                                                                    
		printf("%s \n%s \n  \n",command->args[0],command->args[1]);
		int hour;
		int minute;
		char dotPunc[] = ".";
		char *alarmTime = strtok(command->args[0],dotPunc);   //get the time in a char ptr
		if(alarmTime != NULL){
			hour = atoi(alarmTime);  //get the hour
			minute = atoi(strtok(NULL,dotPunc));  //get the minute
		}
		char currentDir[200];//"/home/lutfu/Music/ms1";
		char buf[100];
		getcwd(buf,1024);  //get the current working directory.
		strcpy(currentDir,buf);
		strcat(currentDir,"/");  	 
		FILE *file_ptr = fopen("currentDir", "w");  //open the file that will be written on the crontab with exec
		fprintf(file_ptr,"XAUTHORITY=~/.Xauthority\n");
		fprintf(file_ptr,"DISPLAY=:0.0\n");
		fprintf(file_ptr, "%d %d * * * rhythmbox\n", minute, hour);
		fprintf(file_ptr, "%d %d * * * rhythmbox-client enqueue %s\n", minute, hour,command->args[1]);
		fprintf(file_ptr, "%d %d * * * rhythmbox-client --play\n", minute, hour);
		fclose(file_ptr); //close the file
		pid_t pid;
		pid = fork();
		if(pid == 0) {
		char *cronArgs[] = {"/usr/bin/crontab","currentDir",NULL};   
		execv(cronArgs[0], cronArgs);
		} else {
		     waitpid(pid, NULL, 0);	
		}
		remove("currentDir"); 
		return SUCCESS;		
				}
				
		//End of part 4	//End of part 4	//End of part 4	//End of part 4	//End of part 4	//End of part 4	//End of part 4	
				
	
	//Part 5	//Part 5	//Part 5	//Part 5	//Part 5	//Part 5	//Part 5	//Part 5	//Part 5	//Part 5	//Part 5	
	if (strcmp(command->name, "kdiff")==0){
			if(strcasecmp(command->args[0],"-b")!=0){
				    FILE *fp1;
				    FILE *fp2;
				    int lineNo = 1;
				    char line1[500];
				    char line2[500];
				   if((strstr(command->args[1],".txt")==NULL) || (strstr(command->args[2],".txt")==NULL)){
				   	printf("You did not enter txt file.\n");
				   }
				   fp1 = fopen(command->args[1],"r");
				   fp2 = fopen(command->args[2],"r");
				    int noDiff=0;
				    while (fgets(line1,500,fp1)!=NULL && fgets(line2,500,fp2)!=NULL){ 
					if(strcmp(line1,line2) != 0){	   
					    printf("%s:Line number %d: %s",command->args[1], lineNo,line1);
					    printf("%s:Line number %d: %s", command->args[2], lineNo,line2);	    
					    noDiff++;
					}    
						 lineNo++;   
				    }
				fclose(fp1);
				fclose(fp2);
				if(noDiff ==0) {
					printf("The files are identical.\n");
				}
				else{
					printf("%d different lines found.\n",noDiff);	
				}
			}
			else if(strcasecmp(command->args[0],"-b")==0){	
			    FILE* ptr1; 
			    ptr1 = fopen(command->args[1], "r");   //opening the first file			   
			    if (ptr1 == NULL) {   // checking if the file exist or not
				printf("File does not exist.\n");
				return -1;
			    }
			    fseek(ptr1, 0L, SEEK_END);   // calculating the size of the file
			    long int size1 = ftell(ptr1);
			    fclose(ptr1);   // closing the file
			    FILE* ptr2;
			    ptr2 = fopen(command->args[2], "r");  //opening the second file
			    if (ptr2 == NULL) { // checking if the file exist or not
				printf("File does not exist.\n");
				return -1;
			    }
			    fseek(ptr2, 0L, SEEK_END);    // calculating the size of the file
			    long int size2 = ftell(ptr2);  // closing the file
			    fclose(ptr2);
			    int diff = size1-size2;
			    if(diff<0){   //handle when the diff is negative(size1 < size2)
			    	diff = -diff;
			    }
			    if(size1 == size2){
			    	printf("The two files are identical\n");
			    }
			    else{
			    	printf("The two files are different in %d bytes\n",diff);
			    }
		}
		
	}
	//End of part 5	//End of part 5	//End of part 5	//End of part 5	//End of part 5	//End of part 5	//End of part 5	//End of part 5							
	
	
	
	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	// Part 6	
	if (strcmp(command->name, "soshellMedia")==0){
		char socialMedia[10][20] = {"Youtube","Facebook","Instagram","Twitter","Discord", "Whatsappweb","Reddit"};  //Store the choices in an Array
		char socialMediaUrl[10][50] = {"https://www.youtube.com/",    //Store the links in an array
		"https://www.facebook.com/","https://www.instagram.com/",
		"https://twitter.com/","https://discord.com/","https://web.whatsapp.com/","https://www.reddit.com/"};			
			pid_t pid_social=fork();
			if(pid_social==0){   //child process is executing
			printf("Enter the social media id you want to enter to.\nEnter -1 to quit.\n");
			for(int i = 0 ; i < 7 ; i++){
				printf("%d) %s\n",i,socialMedia[i]);
				}
					int choice;
					printf("Please enter your choice: \n");
					scanf("%d",&choice);
					printf("You entered your choice.\n");
					if(choice==0){
						char temp;
						char channelToSearch[150];
						strcpy(channelToSearch,socialMediaUrl[0]);
						strcat(channelToSearch,"results?search_query="); //edit the url for searching 
						printf("What video do you want to search for in youtube: \n");
						char channel[40];
						scanf("%c",&temp);
						fgets(channel, 40, stdin);
						strcat(channelToSearch,channel);
						printf("Now opening %s ...\n",socialMedia[choice]);
						execlp("/usr/bin/firefox","/usr/bin/firefox", channelToSearch, (char *)NULL);  //call exec to open firefox tab.
						}
					else if(choice==1){				
						execlp("/usr/bin/firefox","/usr/bin/firefox",socialMediaUrl[choice], (char *)NULL);    //call exec to open firefox tab.
						}
					else if(choice==2){
						execlp("/usr/bin/firefox","/usr/bin/firefox", socialMediaUrl[choice], (char *)NULL);  //call exec to open firefox tab.
						}
					else if(choice==3){
						char temp;
						char channelToSearch[100];
						strcpy(channelToSearch,socialMediaUrl[3]);
						strcat(channelToSearch,"search?q=");  //edit the url for searching
						printf("What idea do you want to search for in twitter: \n");
						char channel[40];
						scanf("%c",&temp);
						fgets(channel, 40, stdin);
						strcat(channelToSearch,channel);
						strcat(channelToSearch,"&src=typed_query");
						printf("Now opening %s ...\n",socialMedia[choice]);
						execlp("/usr/bin/firefox","/usr/bin/firefox", channelToSearch, (char *)NULL);  //call exec to open firefox tab.
						}
					else if(choice==4){
						execlp("/usr/bin/firefox","/usr/bin/firefox", socialMediaUrl[choice], (char *)NULL);  //call exec to open firefox tab.
						}
					else if(choice==5){
						execlp("/usr/bin/firefox","/usr/bin/firefox", socialMediaUrl[choice], (char *)NULL);  //call exec to open firefox tab.
						}
					else if(choice==6){
						char temp;
						char channelToSearch[100];
						strcpy(channelToSearch,socialMediaUrl[6]);
						strcat(channelToSearch,"search/?q=");   //edit the url for searching
						printf("What discussion do you want to search for on reddit: \n");
						char channel[40];
						scanf("%c",&temp);
						fgets(channel, 40, stdin);
						strcat(channelToSearch,channel);
						printf("Now opening %s ...\n",socialMedia[choice]);
						execlp("/usr/bin/firefox","/usr/bin/firefox", channelToSearch, (char *)NULL);   //call exec to open firefox tab.
						}
					else if(choice == -1 ){
						printf("Quitting soshellMedia...\n");
						}
					else{  //handling the invalid cases.
						printf("You did not enter valid number.\n");
					}
		}
		  else{     //In case there is an error, perror does the error handling.
	 	  	printf("\nexeclp returned! errno is [%d]\n",errno);
   			perror("The error message is :\n\n");
			wait(0);
			  }
	}
	//End of part 6	//End of part 6	//End of part 6	//End of part 6	//End of part 6	//End of part 6	//End of part 6	//End of part 6	
	
	
	
	
	
	
	
	
	

	pid_t pid=fork();
	if (pid==0) // child
	{
		/// This shows how to do exec with environ (but is not available on MacOs)
	    // extern char** environ; // environment variables
		// execvpe(command->name, command->args, environ); // exec+args+path+environ

		/// This shows how to do exec with auto-path resolve
		// add a NULL argument to the end of args, and the name to the beginning
		// as required by exec

		// increase args size by 2
		command->args=(char **)realloc(
			command->args, sizeof(char *)*(command->arg_count+=2));

		// shift everything forward by 1
		for (int i=command->arg_count-2;i>0;--i)
			command->args[i]=command->args[i-1];

		// set args[0] as a copy of name
		command->args[0]=strdup(command->name);
		// set args[arg_count-1] (last) to NULL
		command->args[command->arg_count-1]=NULL;

		execvp(command->name, command->args); // exec+args+path
		exit(0);
		/// TODO: do your own exec with path resolving using execv()
		
		
		//Part 1	//Part 1	//Part 1	//Part 1	//Part 1	//Part 1	//Part 1	//Part 1	//Part 1	//Part 1	
		
		
		      command->args=(char **)realloc(command->args, sizeof(char *)*(command->arg_count+=2)); //allocate memory for args
				for (int i=command->arg_count-2;i>0;--i){   //put the args into the allocated memory
				command->args[i]=command->args[i-1];
			}
			command->args[0]=strdup(command->name);
			//execv(command->name, command->args); // exec+args+path			 	
			 char path[200] = "/bin/";   //give the path 
			 strcat(path, command->args[0]);  //complete the path
			 execv(path, command->args);    //call execv with the command


		
		
		
		//End of part 1	//End of part 1	//End of part 1	//End of part 1	//End of part 1	//End of part 1	//End of part 1	
	}
	else
	{
		if (!command->background)
			wait(0); // wait for child process to finish
		return SUCCESS;
	}

	// TODO: your implementation here
	
	printf("-%s: %s: command not found\n", sysname, command->name);
	return UNKNOWN;
}
