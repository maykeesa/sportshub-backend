package br.com.sporthub.estabelecimento

import br.com.sporthub.estabelecimento.form.EstabelecimentoForm
import br.com.sporthub.service.GenericService
import br.com.sporthub.usuario.Usuario
import br.com.sporthub.usuario.UsuarioRepository
import br.com.sporthub.usuario.form.UsuarioForm
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class EstabelecimentoService(repository: EstabelecimentoRepository) : GenericService<Estabelecimento>(repository) {

    fun atualizarEstabelecimento(id: UUID, estabelecimentoForm: EstabelecimentoForm): Optional<Estabelecimento> {
        return atualizarEntidade(id, estabelecimentoForm)
    }

}