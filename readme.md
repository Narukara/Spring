### Spring - Chess Game

---

我在一部古老的Nokia手机上遇到的不知名的棋类游戏。它原本是一款名为“米奇的世界”的游戏附带的小游戏，但我发现它比游戏本体还要有趣。

这是它的[游戏规则](rule.md)。因其扩散的特性，我的朋友将他命名为Spring（涌泉）。

Spring的特点在于局势的急剧变化：到游戏中后期，一枚棋子往往就能引起整个棋盘翻天覆地的变化。

---

已经基本开发完成的模块：

- Chessboard：棋盘，包括规则的实现和对局记录
- MatchThread/MatchManager：对局管理

- HumanPlayer：供人类玩家游玩
- RandomPlayer：一个疯狂的随机AI
- ExperientialPlayer：基于经验的AI，时强时弱
- SearchTreePlayer：基于搜索树和经验的AI，理论上应该比ExperientialPlayer强
- OnlinePlayer/LocalPlayer：用于联机
- ANN相关，在ann包下

计划开发的模块：

- SpringANN：一种针对Spring规则优化的ANN。以前我尝试过普通的全连接ANN，效果不佳
- 用于InheritablePlayer进行训练的管理器

---

顺带一提，这是我第三次重构这个项目了