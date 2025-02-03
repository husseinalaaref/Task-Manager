# Hussein Al Aaref Personal Project


## Task Manager for Everyone:

**1. What my Task Manager will do?**

  - Add and save new tasks/to-dos. 
  - Mark completed tasks as "done" or "past tasks". 
  - Navigate through tasks to find a specific one based on name of course, time takes to complete the task, or due-date of task. 
  - Alert students/user 48 hours prior to task's deadline. 


**2. Who will use the Task Manager?**

  - University Students with muti-course schedule. 
  - Professionals who have strict taks's deadlines. 
  - Productivity savvies who value time and progress.  


**3. Why I can't wait to release the final product of the Task Manager?**

- Personally, I have been struggling with time managment throughout my acaemic career. I can not count the times where I missed important deadlines and events, because I decided to store them in my brain memory (fish memory ^_^). as a computer science student, I made the decision to leverage the technical skills I am learning in software construction to solve this problem once for all. I also understand that other students, proffesionals, and the average person would like to see a practical solution for this problem, and thereforeshare the same excitment with me about the release of this application.  


## User Stories: 

  - As a user, I want to be able to add a task to my Tasks list and include the task name, time takes to complete the task, and the due-date of that task.
  - As a user, I want to know the total time (in minutes) that will take me to complete the tasks in my Tasks list.  
  - As a user, I want to be able to view the list of tasks on my Tasks list. 
  - As a user, I want to be able to mark a task as done/complete on my tasks list. 
  - As a user, I want to be able to remove a task from my Tasks list.
  - As a user, I want to be able to see the number of incomplete and number of complete tasks in my Tasks list. 
  - As a user, I want to get a notification 48 hours proior to my task's deadline or if the task is due within 48 hours.
  - As a user, I want to be able to save my tasks list to the file.
  - As a user, I want to be able to be able to load my tasks list from the file.


## Instructions for End User: 

  - Adding multiple tasks to the list of tasks: you can add a task or multiple of tasks to your list of tasks by entering the name, time required, and deadline (yyyy-mm-dd hh:mm), to the corresponding fields at the bottom of the menu. Then, press the option tab on the upper left corner and choose 'add task'.  
  - Viewing a task or multiple tasks from list of tasks: you can view a task or multiple tasks from your list of tasks by press in the task appearing in the display then press the option tab on the upper right corner of the menu and press view task. 
  - Removing a task or multiple tasks from list of tasks: you can remove a task or multiple tasks from your list of tasks by press in the task appearing in the display then press the option tab on the upper right corner of the menu and press remove task.
  - Get the Tasks with the rqruired time to complete greater or equal to x time(e.g. tasks that take more than or one hour to complete): you can get the task or multiple of tasks that take greater or equal time to complete than x time from your list of tasks by inserting a time (integer) to the time required corresponding box and then press the bottom "Filter by time required" right next to it. 
  - You can save the state of my application by press on the option tab in the upper right corner and click on "Save tasks" tab. 
  - You can reload the state of my application by press press on the option tab in the upper right corner and click on "Save tasks" tab. 


  ## Phase 4: Task 2
  - Application is closing. Event log:
Tue. Nov. 26 09:36:47 PST 2024
Test event: Application started.
Tue. Nov. 26 09:36:54 PST 2024
Data loaded: Tasks loaded from tasks.dat.
Tue. Nov. 26 09:37:01 PST 2024
Task viewed: CHEM 121
Tue. Nov. 26 09:37:31 PST 2024
Task added: MATH 307
Tue. Nov. 26 09:37:39 PST 2024
Task removed: CHEM 121
Tue. Nov. 26 09:37:44 PST 2024
Task viewed: MATH 307
Tue. Nov. 26 09:37:50 PST 2024
Tasks filtered: Time required > 60
Tue. Nov. 26 09:38:06 PST 2024
Data loaded: Tasks loaded from tasks.dat.
Tue. Nov. 26 09:38:10 PST 2024



## Phase 4: Task 3 
- Looking at the UML class diagram for the Task Manager project, I think one way to improve the
design would be to separate the user interface logic in TaskManagerGUI from the core functionality.
Right now, this class handles both, which goes against the Single Responsibility Principle. If I
had more time, I would create a Controller class to act as a middleman between the GUI and the task
management logic. This would make the design more organized and easier to manage.

- Another improvement would be to clean up the TaskManagerGUI constructor. While I’ve already split
it into smaller methods for better readability and checkstyle adherence, the use of anonymous inner classes for event handling could be replaced with dedicated classes. This would make the code simpler and easier to update in the future. These changes would make the project more maintainable and easier to scale without adding new features.
