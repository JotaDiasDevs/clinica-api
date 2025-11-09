@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/teste")
    public String teste() {
        return "Controller funcionando!";
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        // ... código existente
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        // ... código existente
    }
}