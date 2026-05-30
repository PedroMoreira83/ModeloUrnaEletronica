# Simulação de Urna Eletrônica - LPOO

Este projeto consiste em um **Simulador de Urna Eletrônica** desenvolvido em Java, focado na aplicação prática dos conceitos de Linguagem de Programação Orientada a Objetos (LPOO). O sistema foi estruturado de forma híbrida (Terminal/Console + Interface Gráfica Java Swing) para demonstrar com clareza a separação de responsabilidades e as regras do processo eleitoral brasileiro de forma simplificada.

Projeto desenvolvido para a disciplina de LPOO sob a avaliação do **Prof. José Cleyton**.

---

## 🛠️ Funcionalidades e Arquitetura

O projeto foi dividido em quatro grandes épicos lógicos:

1. **Épico 1: Modelagem de Domínio e Cadastros Base**
   - Criação de classes abstratas (`Pessoa`) e especializações via herança (`Eleitor` e `Candidato`).
   - Gestão de Pleitos eleitorais gerenciados inteiramente em memória RAM.
2. **Épico 2: Motor de Votação e Regras de Negócio**
   - Lógica de captação de votos (Válidos, Brancos e Nulos) de forma estritamente anônima.
   - Criação de contratos de negócios via Interfaces (`OperacaoUrna`).
3. **Épico 3: Interface Gráfica (GUI)**
   - Tela de votação fiel ao layout da urna eletrônica real desenvolvida em Java Swing.
   - Carregamento dinâmico de informações e fotos dos candidatos a partir de inserções numéricas em tempo real.
4. **Épico 4: Encerramento e Apuração**
   - Sistema de trava automática ao atingir a capacidade máxima configurada da seção.
   - Geração de Súmula Eleitoral detalhada com cálculos estatísticos e percentuais específicos por cargo.

---

## 📁 Estrutura de Pacotes do Projeto

Para garantir os princípios de encapsulamento e arquitetura limpa, o código deve ser organizado na seguinte estrutura de diretórios dentro da pasta raiz:

```text
📁 src
 ├── 📁 exceptions
 │    ├── EleicaoInativaException.java
 │    └── EleitorNaoCadastradoException.java
 │
 ├── 📁 interfaces
 │    └── OperacaoUrna.java
 │
 ├── 📁 main
 │    └── Principal.java
 │
 ├── 📁 models
 │    ├── Candidato.java
 │    ├── Cargo.java
 │    ├── Eleitor.java
 │    ├── Pessoa.java
 │    ├── Pleito.java
 │    ├── TipoVoto.java
 │    └── Voto.java
 │
 ├── 📁 services
 │    ├── GeradorSumula.java
 │    └── UrnaEletronica.java
 │
 └── 📁 views
      └── TelaUrna.java
