module seabattleclient {
  requires slf4j.api;
  requires javafx.graphics;
  requires javafx.controls;
  requires SeaBattleLogin;

  exports seabattlegui;
}