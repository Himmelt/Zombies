# Zombies Plugin for Spigot

自定义僵尸生成插件

### 指令
```
/zombie slot [0|1|2|3]          查看/设置 记分板显示位置
/zombie allow [world]           允许 当前世界/[world]世界 在玩家周围生成僵尸
/zombie disallow [world]        禁止 当前世界/[world]世界 在玩家周围生成僵尸
```

### 配置
```yaml
#僵尸行走速度随机范围(迅捷药水效果等级)
speed: 0-2
#僵尸血量随机范围
health: 1-3
#僵尸随机生成位置距离玩家的半径范围
spawnRadius: 15-45
#僵尸生成(刷新)时间(tick,1秒 == 20 tick)
refresh: 50
#玩家周围僵尸数量上限
spawnLimit: 10
#在冷却时间内的击杀上限，
#击杀数到达该值后，周围不再继续生成僵尸，超过冷却时间后继续生成。
killCoolLimit: 20
#击杀数量刷新时间(冷却时间)(秒)
killCoolTime: 300
#生成小僵尸的概率
babyChance: 0.3
#击杀僵尸自定义掉落物品，true:自定义掉落；false：正常掉落
customDrops: true
#击杀僵尸自定义经验掉落范围
dropExp: 5-7
#记分板显示位置，0：不显示记分板 1:头顶 2:玩家列表 3:屏幕右侧
displaySlot: 1
#允许生成僵尸的世界名(列表)
allowWorlds:
- world
```
