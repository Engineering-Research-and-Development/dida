# Hardware Resources Requirements 

## Spark Jobs

To understand how to allocate resources, here some simple formulas:
- **n_nodes** : total number of machines
- **machine_cores** : number of cores per machine
- **machine_mem** : memory for each machine
- **executor_max_vcores**: From research, 5
- **tot_machine_executors**: floor((machine_cores - 1)/5)
- **tot_executors**: (tot_machine_executors * n_nodes) - 1
- **optimal_executor_memory**: ((machine_mem / tot_machine_executor)-1g) - (((machine_mem / tot_machine_executor)-1g) \* 0.07)

Each spark-submit execution has some command options that can be changed by the user based on needs. Here the list of arguments modifiable with the minimum/maximum/suggested amount of resources:
- **num_executors**: 1 / tot_machine_executors / 2
- **executor_cores**: 1 / 4 / 4 
- **driver_memory** : 512m / 4g / 1g
- **executor_memory** :512m / 4g / min(4g, max(512, optimal_executor_memory/(2 * total_number_of_algs)))

Moreover there are more things to consider:
- YARN doubles the amount of memory allocated for execution, meaning that 512m declared becomes 1g effective
- Suggested amount of resources has to be limited due to machine limits, setting them to the highest possible maximum
- Minimum and maximum amount of resources can be expanded in yarn configuration
- For small algorithms / if resources are not enough (check the scheduler on YARN service to see the resources) it is suggested to use the following settings:
  - **num_executors**: 2
  - **executor_cores**: 1
  - **driver_memory** : 512m
  - **executor_memory** : 512m
