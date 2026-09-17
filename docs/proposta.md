# Proposta do Produto — Revysa

## Visão do produto

Para estudantes universitários, técnicos, de concursos e autodidatas que têm dificuldade em transformar leitura em retenção real, o **Revysa** é uma plataforma mobile de estudo ativo que conecta materiais, leitura, compreensão, recuperação ativa e revisão espaçada. Diferente de leitores de PDF e aplicativos focados apenas em flashcards, o Revysa mantém o progresso, as interações e as revisões associados ao conteúdo original.

## Problema

Materiais de estudo ficam espalhados entre PDFs, livros, slides, anotações e aplicativos diferentes. Além disso, ler passivamente não indica se o conteúdo foi compreendido nem quando precisa ser revisado. Criar flashcards manualmente também é trabalhoso e desconectado do material estudado.

## Público-alvo

- estudantes universitários;
- estudantes de cursos técnicos;
- pessoas estudando tecnologia;
- estudantes de concursos;
- autodidatas que utilizam livros, apostilas e PDFs.

O foco inicial é o estudo individual, sem recursos sociais.

## Proposta de valor

O Revysa orienta o estudante sobre o que estudar, permite interagir com o material, registrar compreensão e revisar conceitos antes que sejam esquecidos. O MVP não depende de IA: a proposta central funciona com materiais cadastrados e ações manuais.

## Hipótese de valor

Acreditamos que estudantes terão mais consistência e retenção quando puderem ler, testar a compreensão e revisar o mesmo conteúdo em um único fluxo, porque reduzirão a fragmentação entre materiais, anotações e flashcards.

## MVP

### Dentro

Cadastro e login, disciplinas, biblioteca, materiais cadastrados, capítulos, leitura estruturada, progresso, destaques, notas, flashcards, quizzes, revisão espaçada, revisão diária, acompanhamento de domínio, funcionamento offline e sincronização.

### Fora

Rede social, grupos de estudo, colaboração em tempo real, marketplace, distribuição de livros protegidos, gestão de turmas, decks públicos, gamificação complexa, importação do Anki, calendário acadêmico, Google Calendar, chat genérico com IA, OCR obrigatório, geração automática obrigatória de conteúdo e upload arbitrário de PDF como requisito central.

## Plataforma e backend

O alvo prioritário será Android, por ser adequado ao público estudantil e permitir desenvolvimento e distribuição com baixo custo. Desktop será alvo secundário para acelerar o desenvolvimento e validar a interface compartilhada.

O backend escolhido é o Supabase, por oferecer autenticação, PostgreSQL e armazenamento em uma camada gratuita. Ele reduz o esforço de infraestrutura de uma equipe individual e atende às necessidades futuras de dados, autenticação e sincronização.

## Equipe e processo

Equipe de uma pessoa:

- **João Felipe de Holanda** — concepção, produto, desenvolvimento, documentação, testes e apresentação.

O trabalho será rastreado por issues e pull requests vinculados, com commits pequenos e documentação das decisões. A coorte será a B como padrão de planejamento caso ainda não tenha sido definida oficialmente.

## Riscos

Os principais riscos são escopo excessivo, complexidade do offline-first, dependência de backend e direitos autorais dos materiais. O controle será feito por escopo incremental, materiais licenciados e adiamento da infraestrutura real para as sprints correspondentes.
