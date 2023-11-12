# java_21_virtual_threads_research
This repo contains some research on java 21's virtual threads and record usage

Some stats below:

2023-11-12T06:55:52.792Z  INFO 4645 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 100000 trades is 8.3336278166 seconds using separate virtual threads
2023-11-12T07:07:23.087Z  INFO 5027 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 1000000 trades is 75.7923965834 seconds using separate virtual threads
2023-11-12T08:49:55.129Z  INFO 6850 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 10000000 trades is 801.4820799832 seconds using separate virtual threads

2023-11-12T06:59:59.471Z  INFO 4944 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 100000 trades is 8.4046415834 seconds using Thread.ofVirtual().start()
2023-11-12T07:16:53.066Z  INFO 5411 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 1000000 trades is 78.0903209584 seconds using Thread.ofVirtual().start()
2023-11-12T10:18:18.400Z  INFO 10515 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 10000000 trades is 780.531863625 seconds using Thread.ofVirtual().start()

2023-11-12T07:41:21.595Z  INFO 6728 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 100000 trades is 7.7804777832 seconds using blocking call
2023-11-12T07:38:56.165Z  INFO 6242 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 1000000 trades is 80.8098665666 seconds using blocking call
2023-11-12T12:12:53.484Z  INFO 15053 --- [trade-service] [           main] com.cham.trade.TradeApplication          : Time taken to persist 10000000 trades is 740.7852014084 seconds using blocking call
