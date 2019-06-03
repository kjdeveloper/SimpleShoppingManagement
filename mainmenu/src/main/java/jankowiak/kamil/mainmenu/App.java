package jankowiak.kamil.mainmenu;

import jankowiak.kamil.mainmenu.menu.MenuService;

public class App {
    public static void main(String[] args) {
       /*DataGeneratorService dataGeneratorService = new DataGeneratorService();
        dataGeneratorService.saveToFile("C:\\Programowanie\\ShoppingManagementFinal\\persistence\\src\\main\\java\\jankowiak\\kamil\\persistence\\resources\\shoppingManagementOrderList.json");*/

        var filename = "C:\\Users\\Admin\\Desktop\\ShoppingManagement\\persistence\\src\\main\\java\\jankowiak\\kamil\\persistence\\resources\\shoppingManagementOrderList.json";
        var menuService = new MenuService(filename);
        menuService.mainMenu();
    }
}
