package clientSide.services;

import java.util.Stack;

public class NavigationStackService {

    public final Stack<Integer> menuPages = new Stack<>();

    public NavigationStackService() {
    }

    protected Stack<Integer> getMenuPages() {
        return menuPages;
    }
}
