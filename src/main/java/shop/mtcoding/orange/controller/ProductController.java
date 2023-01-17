package shop.mtcoding.orange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.orange.model.Product;
import shop.mtcoding.orange.model.ProductRepository;
import org.springframework.web.bind.annotation.RequestBody;

@Controller // viewResolve 발동

public class ProductController {

    @Autowired
    private HttpSession session;

    @Autowired // 자식 type으로 찾아냄
    private ProductRepository productRepository;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/redirect")
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException { // test.jsp 파일
                                                                                                        // // 못찾을 예외
        // HttpSession session = request.getSession();
        session.setAttribute("name", "session metacoidng"); // 더 가까운것 부터 찾는다 page>request>session
        request.setAttribute("name", "metacoding"); // key, value / basin 개념 : 자바 hashmap , = model.addAttribut 와 같은 것
        response.sendRedirect("redirect:/test");
    }

    @GetMapping("/dispatcher")
    public void dispatcher(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dis = request.getRequestDispatcher("/test");
        dis.forward(request, response);
    }

    @GetMapping({ "/", "/product" }) // /api : 데이터 리턴
    public String findAll(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "product/main"; // request dispatcher

        // try {
        // RequestDispatcher dis =. request.getRequestDispatcher("풀경로/main.jsp"); 자동으로
        // 내부에서 완성, 따로 코드는 작성 X
        // dis.forward(request, response);
        // } catch (Exception e) {
        // e.printStackTrace();
        // model 은 request
        // }
    }

    @GetMapping("/product/{id}")
    public String findOne(@PathVariable int id, Model model) {
        Product product = productRepository.findOne(id);
        model.addAttribute("product", product);
        return "product/detail";

    }

    // @GetMapping("/api/product") // /api : 데이터 리턴
    // @ResponseBody // RestController 로 작동
    // public List<Product> apiFindAllProduct() {
    // List<Product> productList = productRepository.findAll();
    // return productList;
    // }

    // @GetMapping("/api/product/{id}") // PathVariable
    // @ResponseBody // RestController 로 작동
    // public Product apiFindOneProduct(@PathVariable int id) {
    // Product product = productRepository.findOne(id); // #{id} 여기에 꽂힘
    // return product;
    // }
    // // Controller 니까 jsp 에 담아서 뿌린다

    @GetMapping("/product/addForm")
    public String addForm() {
        return "product/addForm";
    }

    @PostMapping("/product/add")
    public String add(String name, int price, String qty) { // 적는 데이터 타입에 따라 자동 파싱, w-xxx 타입으로 받는 다는 것
        int result = productRepository.insert(name, price, qty); // 메서드 생성해야 함
        if (result == 1) {
            System.out.println("insert 성공");
            return "redirect:/product";
        } else {
            return "redirect:/product/addForm";
        }
    }

    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable int id) {
        int result = productRepository.delete(id);
        if (result == 1) {
            System.out.println("delete 성공");
            return "redirect:/";
        } else {
            return "redirect:/product/" + id;
        }
    }

    @GetMapping("/product/{id}/updateForm")
    public String upadateForm(@PathVariable int id, Model model) {
        Product product = productRepository.findOne(id);
        model.addAttribute("product", product); // 담는 과정 response, MVC 패턴 동작
        return "product/updateForm";
    }

    @PostMapping("/product/{id}/update")
    public String upadate(@PathVariable int id, String name, int price, int qty) {
        int result = productRepository.update(id, name, price, qty);
        if (result == 1) {
            return "redirect:/product/" + id;
        } else {
            return "redirect:/product/" + id + "/updateForm";
        }
    }

}
