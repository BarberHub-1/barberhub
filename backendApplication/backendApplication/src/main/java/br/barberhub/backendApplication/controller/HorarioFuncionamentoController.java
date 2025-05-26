package br.barberhub.backendApplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




import br.barberhub.backendApplication.service.HorarioFuncionamentoService;

@RestController
@RequestMapping("/api/horario_intervalos")
public class HorarioFuncionamentoController {

    @Autowired
    private HorarioFuncionamentoService horariointervaloService;

	/*
	 * @PostMapping public ResponseEntity<HorarioIntervalo>
	 * cadastrarIntervalo(@Valid @RequestBody IntervaloDTO intervaloDTO) { return
	 * ResponseEntity.ok(horariointervaloService.cadastrarIntervalo(intervaloDTO));
	 * }
	 */

	/*
	 * @GetMapping public ResponseEntity<List<HorarioIntervalo>> listarIntervalos()
	 * { return ResponseEntity.ok(horariointervaloService.listarIntervalos()); }
	 * 
	 * @GetMapping("/{id}") public ResponseEntity<HorarioIntervalo>
	 * buscarIntervaloPorId(@PathVariable Long id) { return
	 * ResponseEntity.ok(horariointervaloService.buscarIntervaloPorId(id)); }
	 * 
	 * @GetMapping("/profissional/{profissionalId}") public
	 * ResponseEntity<List<HorarioIntervalo>>
	 * buscarIntervalosPorProfissional(@PathVariable Long profissionalId) { return
	 * ResponseEntity.ok(horariointervaloService.buscarIntervalosPorProfissional(
	 * profissionalId)); }
	 * 
	 * @GetMapping("/estabelecimento/{estabelecimentoId}") public
	 * ResponseEntity<List<HorarioIntervalo>>
	 * buscarIntervalosPorEstabelecimento(@PathVariable Long estabelecimentoId) {
	 * return
	 * ResponseEntity.ok(horariointervaloService.buscarIntervalosPorEstabelecimento(
	 * estabelecimentoId)); }
	 */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirIntervalo(@PathVariable Long id) {
    	horariointervaloService.excluirIntervalo(id);
        return ResponseEntity.ok().build();
    }
} 
