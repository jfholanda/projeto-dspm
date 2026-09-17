# PRD Inicial — Plataforma Mobile de Estudo Ativo

## 1. Visão geral

O produto será um aplicativo mobile de estudos que combina:

* leitura ativa;
* organização de materiais por disciplina e capítulo;
* flashcards;
* quizzes;
* perguntas contextuais;
* notas e destaques;
* repetição espaçada;
* acompanhamento de progresso;
* funcionamento offline.

A proposta não é criar apenas um clone do Anki, nem apenas um leitor de PDFs com IA.

O produto deve funcionar como uma plataforma de estudo guiado, em que o estudante:

1. acessa um material;
2. lê o conteúdo;
3. interage com trechos importantes;
4. testa sua compreensão;
5. transforma conteúdos em flashcards;
6. revisa posteriormente aquilo que está começando a esquecer.

A experiência deve conectar leitura, compreensão, recuperação ativa e retenção.

---

# 2. Problema

Estudantes normalmente possuem seus materiais espalhados entre:

* PDFs;
* slides;
* livros;
* anotações;
* documentos;
* plataformas de aula;
* aplicativos de flashcards.

Além da fragmentação, existe outro problema relevante:

> o estudante frequentemente sabe o que precisa ler, mas não sabe se realmente aprendeu nem quando deve revisar aquele conteúdo.

Ler passivamente um capítulo não garante retenção.

Criar flashcards manualmente ajuda na revisão, mas exige trabalho e normalmente fica desconectado do material original.

O produto pretende conectar essas duas experiências.

---

# 3. Proposta de valor

O aplicativo centraliza materiais de estudo e transforma a leitura em uma atividade ativa.

O estudante consegue:

* ler um capítulo;
* destacar um trecho;
* fazer uma anotação;
* pedir uma explicação;
* gerar ou criar uma pergunta;
* criar um flashcard;
* responder quizzes;
* acompanhar sua compreensão;
* revisar posteriormente os conceitos estudados.

O objetivo central é responder duas perguntas:

> O que eu deveria estudar agora?

e:

> O que estou começando a esquecer?

---

# 4. Visão do produto

Para estudantes que utilizam livros, PDFs, apostilas e outros materiais para aprender conteúdos extensos,

que têm dificuldade em transformar leitura em retenção real e organizar posteriormente suas revisões,

o **Revysa** é um aplicativo móvel de estudo ativo,

que combina leitura estruturada, recuperação ativa, flashcards e repetição espaçada,

diferente de leitores de PDF tradicionais ou aplicativos focados somente em flashcards,

nosso produto mantém leitura, compreensão e revisão conectadas ao mesmo material e acompanha a evolução do estudante ao longo do conteúdo.

---

# 5. Público-alvo

O público inicial será composto principalmente por:

* estudantes universitários;
* estudantes de cursos técnicos;
* pessoas estudando conteúdos de tecnologia;
* estudantes de concursos;
* autodidatas;
* pessoas que estudam por livros, apostilas e PDFs.

O foco inicial será o estudo individual.

Recursos sociais e colaboração não fazem parte do MVP.

---

# 6. Pilares do produto

O produto será estruturado em quatro pilares.

## 6.1 Leitura

O estudante consome o conteúdo.

Exemplos:

* livro;
* capítulo;
* PDF;
* apostila;
* artigo;
* material textual.

## 6.2 Compreensão

O estudante interage com o conteúdo para entender o que está lendo.

Exemplos:

* destaques;
* notas;
* explicações;
* exemplos;
* perguntas sobre trechos.

## 6.3 Recuperação ativa

O estudante precisa recuperar o conteúdo da memória.

Exemplos:

* flashcards;
* quizzes;
* perguntas abertas;
* completar conceitos;
* perguntas após uma seção.

## 6.4 Retenção

O sistema ajuda o estudante a revisar os conteúdos antes que sejam esquecidos.

Exemplos:

* repetição espaçada;
* fila diária de revisão;
* prioridade de conteúdos;
* histórico de desempenho.

---

# 7. Estrutura conceitual

A organização principal será:

```text
Usuário
 └── Disciplina
      └── Material
           └── Capítulo
                └── Seção
                     ├── Conteúdo
                     ├── Destaques
                     ├── Notas
                     ├── Perguntas
                     ├── Flashcards
                     ├── Quiz
                     └── Progresso
```

Exemplo:

```text
Machine Learning

├── The Hundred-Page Machine Learning Book
│
│   ├── Introduction
│   ├── Linear Regression
│   ├── Logistic Regression
│   ├── Decision Trees
│   └── Neural Networks
│
└── Minhas anotações
```

---

# 8. Biblioteca

O aplicativo terá uma área de biblioteca.

Existirão inicialmente dois tipos de materiais.

## 8.1 Catálogo

Materiais previamente disponibilizados pela aplicação.

Exemplo:

```text
Explorar

Machine Learning

The Hundred-Page Machine Learning Book
[ Começar ]

Introduction to Statistical Learning
[ Começar ]

────────────────────────

Computer Science

Operating Systems
Computer Networks
Algorithms
```

Os materiais disponibilizados pelo aplicativo deverão possuir licença compatível, autorização de uso ou estar em domínio público.

O produto não deverá distribuir livros comerciais protegidos sem autorização.

## 8.2 Minha biblioteca

Conteúdos adicionados pelo próprio usuário.

Exemplo:

```text
Minha biblioteca

Machine Learning — Aula 03.pdf

Redes — TCP IP.pdf

Engenharia de Software — SOLID.pdf

[ Adicionar material ]
```

No MVP inicial, a importação arbitrária de PDF poderá ficar fora do caminho crítico.

---

# 9. Tela inicial

A tela inicial deve apresentar a próxima ação relevante ao estudante.

Exemplo:

```text
Boa tarde

Sua revisão de hoje

18 cartões
3 assuntos
≈ 14 minutos

[ COMEÇAR REVISÃO ]

──────────────────────

Continue lendo

The Hundred-Page Machine Learning Book

Chapter 4
Gradient Descent

68%

[ CONTINUAR ]

──────────────────────

Prioridade de estudo

1. Regularization
2. Gradient Descent
3. Logistic Regression
```

A Home não deve funcionar apenas como dashboard.

Ela deve direcionar a próxima sessão de estudo.

---

# 10. Leitura ativa

A leitura será uma das funcionalidades centrais.

O conteúdo deverá ser apresentado de maneira estruturada.

Exemplo:

```text
Chapter 4
Gradient Descent

Gradient descent is an optimization
algorithm used to minimize...

────────────────────────

[ Destacar ]
[ Fazer nota ]
[ Criar flashcard ]
[ Perguntar ]
```

A leitura poderá ser dividida em blocos ou seções menores para evitar uma experiência equivalente a simplesmente abrir um PDF.

---

# 11. Revelação progressiva

Como evolução do leitor tradicional, algumas sessões poderão utilizar revelação progressiva.

Exemplo:

```text
Linear regression assumes a relationship
between...

[ Continuar ]
```

Ao avançar:

```text
Antes de continuar:

Qual é a principal hipótese feita
sobre a relação entre as variáveis?

[ Responder ]
[ Não sei ]
```

Somente depois a próxima parte do conteúdo é apresentada.

Essa abordagem deverá ser opcional e depender do tipo de material.

---

# 12. Destaques

O usuário poderá selecionar partes relevantes do conteúdo.

Ações possíveis:

```text
[ Destacar ]
[ Nota ]
[ Perguntar ]
[ Flashcard ]
```

Os destaques serão vinculados ao material e à seção correspondente.

O usuário deverá conseguir posteriormente acessar todos os destaques realizados.

Exemplo:

```text
Destaques

Gradient Descent
"The gradient represents..."

Regularization
"L2 regularization adds..."
```

---

# 13. Notas

O estudante poderá criar notas relacionadas a trechos específicos.

Exemplo:

```text
Trecho:

"The learning rate determines..."

Minha nota:

Rever isso junto com convergência.
```

As notas poderão ser utilizadas posteriormente durante revisões.

---

# 14. Perguntar sobre o conteúdo

Uma evolução importante será permitir que o estudante faça perguntas sobre um trecho.

Exemplo:

```text
Trecho selecionado:

"The gradient represents the direction
of steepest ascent."

[ Perguntar ]
```

O estudante poderá perguntar:

```text
Explique de forma mais simples.

Por que isso acontece?

Dê um exemplo.

Como isso se relaciona com gradient descent?
```

A pergunta deve carregar contexto do material.

Por exemplo:

```text
material
capítulo
seção
trecho selecionado
pergunta
```

Isso evita tratar a funcionalidade como um chat genérico.

---

# 15. Ações rápidas sobre um trecho

Após selecionar um trecho:

```text
[ Explicar ]
[ Simplificar ]
[ Exemplo ]
[ Perguntar ]
[ Criar flashcard ]
```

Essas ações poderão futuramente utilizar IA.

No MVP, elas não devem ser necessárias para o funcionamento central do produto.

---

# 16. Flashcards

Flashcards continuarão sendo uma parte importante do produto, mas não serão sua estrutura principal.

Cada flashcard estará associado preferencialmente a:

```text
Disciplina
Material
Capítulo
Seção
```

Um flashcard terá:

```text
Pergunta
Resposta
Origem
Data de criação
Próxima revisão
Intervalo
Número de revisões
```

---

# 17. Criação manual de flashcard

O estudante poderá criar um cartão diretamente.

Exemplo:

```text
Pergunta

Qual é a função da learning rate?

Resposta

Determinar o tamanho das atualizações
dos parâmetros durante a otimização.
```

---

# 18. Criar flashcard a partir da leitura

O estudante poderá selecionar um trecho:

```text
"The learning rate determines the size
of each optimization step."
```

e utilizar:

```text
[ Criar flashcard ]
```

A tela de criação será aberta com referência ao trecho original.

Futuramente, a pergunta e resposta poderão ser sugeridas automaticamente.

---

# 19. Quizzes

Cada capítulo poderá possuir quizzes.

Exemplo:

```text
Linear Regression

Teste seu conhecimento

5 perguntas

[ COMEÇAR ]
```

Pergunta:

```text
Qual é o principal objetivo
do método dos mínimos quadrados?

○ Maximizar variância
○ Minimizar erro quadrático
○ Minimizar número de features
○ Maximizar learning rate
```

---

# 20. Feedback do quiz

Ao finalizar:

```text
Resultado

4 / 5

Você teve dificuldade em:

• regularização;
• interpretação do erro.

[ Revisar conceitos ]
```

O resultado poderá contribuir para a prioridade futura de estudo.

---

# 21. Perguntas abertas

Além de múltipla escolha, o produto poderá utilizar perguntas abertas.

Exemplo:

```text
Explique com suas palavras por que
gradient descent utiliza o gradiente negativo.
```

Inicialmente, a resposta poderá ser autoavaliada pelo estudante.

Posteriormente poderá existir avaliação assistida.

---

# 22. Completar conceito

Outra modalidade de recuperação ativa:

```text
__________ é o processo utilizado
para propagar gradientes pelas camadas
de uma rede neural.
```

Resposta:

```text
Backpropagation
```

Esse formato pode ser especialmente útil para conceitos objetivos.

---

# 23. Revisão espaçada

Os flashcards participarão de uma fila de revisão.

Após revelar a resposta:

```text
Como foi?

[ Errei ]
[ Difícil ]
[ Acertei ]
[ Fácil ]
```

A resposta será usada para calcular a próxima revisão.

---

# 24. Algoritmo inicial

O algoritmo deverá começar simples.

Exemplo:

```text
Errei
→ 1 dia

Difícil
→ intervalo × 1,5

Acertei
→ intervalo × 2

Fácil
→ intervalo × 3
```

O objetivo inicial será possuir um algoritmo:

* previsível;
* testável;
* compreensível;
* independente da interface.

Futuramente poderá ser substituído por SM-2 ou FSRS.

---

# 25. Revisão diária

A Home deverá apresentar cartões pendentes.

Exemplo:

```text
Revisão de hoje

Machine Learning
8 cartões

Redes
5 cartões

Banco de Dados
3 cartões

Total
16 cartões

[ COMEÇAR ]
```

A sessão poderá misturar cartões de diferentes materiais.

---

# 26. Sessão guiada de estudo

Uma evolução importante será permitir sessões de estudo com duração definida.

Exemplo:

```text
Quanto tempo você quer estudar?

[ 10 min ]
[ 20 min ]
[ 30 min ]
```

O aplicativo monta uma sessão.

Exemplo:

```text
20 minutos

5 min
Revisão

10 min
Leitura

5 min
Quiz
```

O objetivo é reduzir a necessidade de o estudante decidir constantemente a próxima ação.

---

# 27. Progresso

O produto deve separar dois conceitos:

## Progresso de leitura

Quanto do material foi consumido.

Exemplo:

```text
Leitura
████████░░
78%
```

## Domínio

Estimativa de retenção e desempenho.

Exemplo:

```text
Domínio
██████░░░░
61%
```

Uma pessoa poderá ter:

```text
Leitura: 100%

Domínio: 54%
```

Isso é esperado.

Ler não significa dominar o conteúdo.

---

# 28. Indicador de domínio

O indicador poderá considerar:

```text
acertos recentes

erros recentes

intervalo médio dos flashcards

número de revisões

tempo desde última revisão

resultado de quizzes
```

O indicador deverá ser apresentado como estimativa de estudo, não como avaliação acadêmica formal.

---

# 29. Prioridade de estudo

A partir do domínio, o sistema poderá sugerir:

```text
Prioridade de estudo

1. Regularization
2. Gradient Descent
3. Neural Networks
```

Essa funcionalidade ajuda o estudante a identificar conteúdos que merecem atenção.

---

# 30. Funcionamento offline

O aplicativo deverá continuar utilizável sem internet.

Deverão continuar disponíveis:

* biblioteca já sincronizada;
* materiais baixados;
* leitura;
* destaques;
* notas;
* flashcards;
* revisão;
* quizzes disponíveis localmente;
* progresso.

As alterações serão sincronizadas posteriormente.

---

# 31. Estratégia offline-first

A aplicação deve priorizar dados locais.

Fluxo:

```text
Interface
   ↓
Banco local
   ↓
Repositório
   ↓
Sincronização
   ↓
Backend
```

A interface não deverá depender diretamente de uma resposta remota para funcionar.

---

# 32. Sincronização

Operações locais poderão possuir estados:

```text
SYNCED
PENDING
FAILED
```

Exemplo:

```text
Review

cardId
rating
reviewedAt
nextReviewAt
syncStatus
```

Quando a rede retornar:

```text
PENDING
   ↓
Backend
   ↓
SYNCED
```

---

# 33. Conta e autenticação

O usuário poderá:

* cadastrar conta;
* autenticar;
* sair;
* restaurar sessão.

A autenticação será necessária principalmente para:

* sincronização;
* backup;
* uso em mais de um dispositivo.

---

# 34. Backend

A opção inicial recomendada é Supabase.

Responsabilidades:

```text
Autenticação

PostgreSQL

Storage

Sincronização

Metadados dos materiais
```

O domínio principal do aplicativo deverá continuar independente do Supabase.

---

# 35. Modelo de dados inicial

## User

```text
id
name
email
createdAt
```

## Subject

```text
id
userId
name
createdAt
updatedAt
```

## Material

```text
id
subjectId
title
description
type
source
coverUrl
createdAt
updatedAt
```

Possíveis tipos:

```text
BOOK
PDF
ARTICLE
TEXT
NOTES
```

## Chapter

```text
id
materialId
title
position
```

## Section

```text
id
chapterId
title
content
position
```

## ReadingProgress

```text
id
userId
materialId
chapterId
sectionId
progress
lastReadAt
```

## Highlight

```text
id
userId
sectionId
selectedText
createdAt
```

## Note

```text
id
userId
sectionId
highlightId
content
createdAt
updatedAt
```

## Flashcard

```text
id
userId
sectionId
question
answer
sourceText
createdAt
updatedAt
```

## CardProgress

```text
id
flashcardId
userId
difficulty
interval
repetitions
lastReviewedAt
nextReviewAt
```

## Review

```text
id
flashcardId
userId
rating
reviewedAt
```

## Quiz

```text
id
chapterId
title
```

## QuizQuestion

```text
id
quizId
type
question
answer
```

## QuizAttempt

```text
id
quizId
userId
score
startedAt
finishedAt
```

## StudySession

```text
id
userId
startedAt
finishedAt
duration
cardsReviewed
questionsAnswered
sectionsRead
```

---

# 36. Navegação principal

Uma estrutura inicial poderá ser:

```text
HOME
 │
 ├── REVISÃO
 │
 ├── BIBLIOTECA
 │      │
 │      ├── EXPLORAR
 │      └── MEUS MATERIAIS
 │
 ├── MATERIAL
 │      │
 │      └── CAPÍTULO
 │            │
 │            ├── LEITURA
 │            ├── NOTAS
 │            ├── FLASHCARDS
 │            └── QUIZ
 │
 ├── PROGRESSO
 │
 └── PERFIL
```

Barra inferior possível:

```text
Início
Biblioteca
Estudar
Progresso
Perfil
```

---

# 37. MVP obrigatório

O MVP deve funcionar sem IA.

## Entram no MVP

* cadastro e login;
* disciplinas;
* biblioteca;
* materiais previamente cadastrados;
* capítulos;
* leitura estruturada;
* progresso de leitura;
* destaques;
* notas;
* criação manual de flashcards;
* flashcards vinculados ao conteúdo;
* repetição espaçada;
* revisão diária;
* quizzes;
* acompanhamento de domínio;
* funcionamento offline;
* sincronização.

---

# 38. Extensões prioritárias

Se houver tempo:

* upload de PDF;
* leitura de PDFs próprios;
* perguntas contextuais;
* explicação de trechos;
* simplificação de trechos;
* geração automática de flashcards;
* geração automática de quizzes;
* perguntas abertas assistidas;
* sessão guiada de estudo;
* notificações.

---

# 39. Fora do MVP

Ficam explicitamente fora do MVP:

* marketplace de materiais;
* venda de livros;
* distribuição de livros protegidos;
* colaboração em tempo real;
* grupos de estudo;
* rankings globais;
* rede social;
* professores gerenciando turmas;
* compartilhamento público de decks;
* gamificação complexa;
* importação do Anki;
* calendário acadêmico completo;
* integração com Google Calendar;
* chat genérico com IA;
* OCR obrigatório;
* geração automática obrigatória de conteúdo.

---

# 40. Recursos nativos

Para a etapa final do projeto, dois recursos possuem forte aderência ao produto.

## Notificações

Exemplo:

```text
Você tem 18 revisões pendentes hoje.
```

## Câmera

O usuário poderá fotografar:

* quadro;
* livro;
* apostila;
* anotação.

Inicialmente a imagem poderá ser anexada a uma nota ou flashcard.

OCR poderá ser uma evolução posterior.

---

# 41. Hipótese de valor

> Acreditamos que estudantes utilizarão regularmente o aplicativo porque poderão transformar a leitura dos seus materiais em uma rotina estruturada de estudo, recebendo indicações sobre o que revisar antes que o conteúdo seja esquecido.

---

# 42. Métricas de produto

Possíveis métricas:

```text
sessões por semana

tempo de leitura

capítulos concluídos

flashcards criados

flashcards respondidos

taxa de acerto

quizzes concluídos

taxa de conclusão da revisão diária

dias consecutivos estudando

revisões atrasadas

materiais ativos
```

---

# 43. Requisitos não funcionais

O aplicativo deverá:

* funcionar parcialmente sem internet;
* manter dados após fechamento;
* evitar perda de progresso;
* sincronizar operações posteriormente;
* tratar falhas de rede;
* possuir modo claro e escuro;
* adaptar interface a diferentes tamanhos;
* proteger credenciais;
* evitar segredos versionados;
* possuir arquitetura testável;
* manter domínio independente da UI;
* manter domínio independente do backend escolhido.

---

# 44. Organização por Sprint

## Sprint 0

* estrutura KMP;
* CI;
* proposta;
* definição do MVP;
* backend;
* primeira tela opcional.

## Sprint 1

Principais telas:

* Home;
* Biblioteca;
* Material;
* Capítulo;
* Leitor;
* Flashcards;
* Quiz;
* Progresso.

Também:

* navegação;
* deep link;
* tema;
* responsividade;
* acessibilidade;
* testes de interface.

## Sprint 2

Arquitetura:

```text
data/
domain/
presentation/
```

Além de:

* ViewModels;
* StateFlow;
* Koin;
* UiState;
* repositories;
* use cases;
* algoritmo de repetição;
* testes de domínio;
* testes de ViewModel.

## Sprint 3

Infraestrutura:

* backend real;
* autenticação;
* Ktor;
* persistência local;
* sincronização;
* offline-first;
* política de conflito;
* tratamento de erros.

## Entrega final

* notificações;
* câmera;
* armazenamento seguro;
* desempenho;
* CI/CD;
* publicação;
* documentação.

---

# 45. Backlog inicial

## P1 — Visualizar biblioteca

Como estudante, quero visualizar os materiais disponíveis para escolher o que estudar.

## P1 — Abrir material

Como estudante, quero abrir um material e visualizar seus capítulos para navegar pelo conteúdo.

## P1 — Ler capítulo

Como estudante, quero ler um capítulo e continuar posteriormente do ponto onde parei.

## P1 — Destacar trecho

Como estudante, quero destacar partes importantes para encontrá-las novamente.

## P1 — Criar nota

Como estudante, quero adicionar uma nota relacionada a um trecho para registrar minha interpretação.

## P1 — Criar flashcard

Como estudante, quero transformar um conceito em flashcard para revisá-lo depois.

## P1 — Revisar flashcard

Como estudante, quero responder flashcards e informar minha dificuldade para programar revisões futuras.

## P1 — Estudar hoje

Como estudante, quero visualizar tudo que preciso revisar hoje para saber por onde começar.

## P1 — Fazer quiz

Como estudante, quero responder perguntas sobre um capítulo para verificar se compreendi o conteúdo.

## P2 — Visualizar progresso

Como estudante, quero visualizar meu progresso de leitura e domínio para identificar assuntos fracos.

## P2 — Utilizar offline

Como estudante, quero continuar estudando sem internet.

## P2 — Sincronizar dados

Como estudante, quero que alterações offline sejam sincronizadas posteriormente.

## P2 — Receber notificações

Como estudante, quero receber lembretes sobre revisões pendentes.

## P3 — Perguntar sobre trecho

Como estudante, quero fazer uma pergunta contextual sobre um trecho para compreender melhor o conteúdo.

## P3 — Gerar flashcard

Como estudante, quero receber uma sugestão de flashcard a partir de um trecho para reduzir trabalho manual.

## P3 — Importar PDF

Como estudante, quero importar meus próprios materiais para estudá-los no aplicativo.

---

# 46. Principais riscos

## Escopo excessivo

Este é o maior risco.

Misturar:

```text
Anki
+
leitor de PDF
+
ChapterPal
+
IA
+
offline
+
biblioteca
```

pode facilmente gerar um produto grande demais.

Mitigação:

> leitura ativa, flashcards e revisão funcionam antes da IA.

## Direitos autorais

A biblioteca própria não poderá ser formada por PDFs comerciais obtidos sem autorização.

Mitigação:

* domínio público;
* licenças abertas;
* materiais autorizados;
* conteúdos próprios.

## Processamento de PDF

PDFs possuem layouts e estruturas variadas.

Mitigação:

> upload arbitrário entra depois da biblioteca estruturada.

## IA

Pode trazer:

* custo;
* latência;
* indisponibilidade;
* respostas erradas;
* aumento grande de escopo.

Mitigação:

> IA permanece opcional.

## Offline-first

É uma das partes tecnicamente mais relevantes e complexas.

Mitigação:

* modelo de conflito simples;
* sem colaboração;
* escrita local primeiro;
* sincronização explícita.

---

# 47. Critério de sucesso do MVP

O MVP estará completo se for possível executar este fluxo:

```text
Criar conta
   ↓
Abrir biblioteca
   ↓
Escolher material
   ↓
Abrir capítulo
   ↓
Ler conteúdo
   ↓
Destacar trecho
   ↓
Criar uma nota
   ↓
Criar flashcard
   ↓
Fazer quiz
   ↓
Fechar aplicativo
   ↓
Retornar posteriormente
   ↓
Continuar leitura
   ↓
Receber flashcards para revisão
```

O fluxo principal deverá continuar funcional quando o usuário estiver temporariamente offline.

---

# 48. Direção do produto

A ordem de evolução deve ser:

```text
1. Ler
2. Compreender
3. Testar
4. Revisar
5. Priorizar
6. Automatizar
7. Aplicar IA
```

A IA não deve definir o produto.

Ela deve melhorar um fluxo de estudo que já funciona sem ela.

A identidade central do produto será:

> **uma plataforma de leitura e estudo ativo que conecta o conteúdo que o estudante está lendo ao que ele precisa revisar depois.**
